package com.moayo.moayoeats.backend.domain.post.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.dto.response.NickMenusResponse;
import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.event.Event;
import com.moayo.moayoeats.backend.domain.order.entity.Order;
import com.moayo.moayoeats.backend.domain.order.repository.OrderRepository;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostCustomRepository;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.post.service.PostService;
import com.moayo.moayoeats.backend.domain.pushEvent.PushEventService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final PushEventService pushEventService;



    @Override
    public void deletePost(PostIdRequest postIdReq, User user) {
        //check if the post exists
        Post post = getPostById(postIdReq.postId());
        //check if the user has a relation with the post
        List<UserPost> userPosts = getUserPostsByPost(post);
        //check if the user is the host of the post
        User host = getAuthor(userPosts);
        if (!host.getId().equals(user.getId())) {
            throw new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_HOST);
        }
        //check the status of the post
        if (post.getPostStatus() == PostStatusEnum.ORDERED
            || post.getPostStatus() == PostStatusEnum.RECEIVED) {
            throw new GlobalException(PostErrorCode.CANNOT_CLOSE_AFTER_ORDERED);
        }

        userPostRepository.deleteAll(userPosts);
        postRepository.delete(post);

        //참가자들에게 알림
        Consumer<User> action = participant -> {
            publisher.publishEvent(
                new Event(participant, NotificationType.MEETING_DELETED));
            pushEventService.postDeleted(
                participant.getId());
        };
        for (UserPost userPost : userPostRepository.findAllByPostAndRoleEquals(post,
            UserPostRole.PARTICIPANT)) {
            User userPostUser = userPost.getUser();
            action.accept(userPostUser);
        }
    }

    @Override
    public void closeApplication(PostIdRequest postIdReq, User user) {
        //check if there is a post with the post id
        Post post = getPostById(postIdReq.postId());
        //check if the user is the host of the post
        List<UserPost> userPosts = getUserPostsByPost(post);
        User host = getAuthor(userPosts);
        checkIfHost(user, host);

        //check if the status is OPEN
        if (post.getPostStatus() != PostStatusEnum.OPEN) {
            throw new GlobalException(PostErrorCode.POST_ALREADY_CLOSED);
        }

        List<Menu> menus = menuRepository.findAllByPost(post);
        //check if sumPrice>minPrice
        if (getSumPrice(userPosts, menus) < post.getMinPrice()) {
            throw new GlobalException(PostErrorCode.LOWER_THAN_MINIMUM_PRICE);
        }

        //delete all menus which are not made by participants
        for (Menu menu : menus) {
            boolean hasRelation = false;
            for (UserPost userPost : userPosts) {
                if (userPost.getUser().getId().equals(menu.getUser().getId())) {
                    hasRelation = true;
                    break;
                }
            }
            if (!hasRelation) {
                menuRepository.delete(menu);
            }
        }

        post.closeApplication();
        postRepository.save(post);

        //참가자들에게 알림
        Consumer<User> action = participant -> {
            publisher.publishEvent(
                new Event(participant, NotificationType.MEETING_ACTIVATED));
            pushEventService.notifyCloseApplication(
                participant.getId());
        };
        for (UserPost userPost : userPostRepository.findAllByPostAndRoleEquals(post,
            UserPostRole.PARTICIPANT)) {
            User userPostUser = userPost.getUser();
            action.accept(userPostUser);
        }

    }

    @Override
    public void completeOrder(PostIdRequest postIdReq, User user) {
        //check if there is a post with the post id
        Post post = getPostById(postIdReq.postId());
        //check if the user is the host of the post
        checkIfHost(user, post);
        //check the status
        if (post.getPostStatus() == PostStatusEnum.OPEN) {
            throw new GlobalException(PostErrorCode.CLOSE_FIRST);
        } else if (post.getPostStatus() == PostStatusEnum.ORDERED
            || post.getPostStatus() == PostStatusEnum.RECEIVED) {
            throw new GlobalException(PostErrorCode.POST_ALREADY_COMPLETED_ORDER);
        }
        post.completeOrder();
        postRepository.save(post);
    }

    @Override
    public void exit(PostIdRequest postIdReq, User user) {
        Post post = getPostById(postIdReq.postId());
        UserPost userPost = getUserPostIfParticipant(user, post);
        checkIfOrdered(post.getPostStatus());
        deleteParticipant(userPost, user, post);

        List<UserPost> userposts = getUserPostsByPost(post);
        int sumPrice = getSumPrice(userposts, post);
        //모인금액이 목표가격의 미만이고, 게시글이 목표금액을 달성한 상태일 때 방장에 알림
        if (sumPrice < post.getMinPrice() && post.getAmountIsSatisfied()) {
            publishEventToHostAndChagePostStatus(post);
        }
    }

    @Override
    public void receiveOrder(PostIdRequest postIdReq, User user) {
        Post post = getPostById(postIdReq.postId());

        //get relations
        List<UserPost> userPosts = getUserPostsByPost(post);
        UserPost userpost = getUserPostByUserIfParticipant(user, userPosts);
        User host = getAuthor(userPosts);

        //check if the status of the post is ordered
        if (post.getPostStatus() != PostStatusEnum.ORDERED) {
            throw new GlobalException(PostErrorCode.ORDER_FIRST);
        }

        //make order for me to review
        Order order = makeAndSaveOrder(post, host, user, UserPostRole.PARTICIPANT);
        //make order for host to review
        Order hostOrder = makeAndSaveOrder(post, user, host, UserPostRole.HOST);

        //relate the order with the menus
        relateOrderWithMenus(user, post, order);

        //delete relation with the post
        userPostRepository.delete(userpost);

        if (userPosts.size() <= 2) {
            post.allReceived();
            relateOrderWithMenus(host, post, hostOrder);
            UserPost hostUserpost = getUserPostByUserIfHost(host, userPosts);
            userPostRepository.delete(hostUserpost);
            postRepository.save(post);
        }
    }

    private void relateOrderWithMenus(User user, Post post, Order order) {
        List<Menu> menus = getUserMenus(user, post);
        for (Menu menu : menus) {
            menu = menu.receive(order);
        }
        menuRepository.saveAll(menus);
    }

    private Order makeAndSaveOrder(Post post, User receiver, User user, UserPostRole role) {
        Order order = Order.builder()
            .receiver(receiver)
            .user(user)
            .store(post.getStore())
            .senderRole(role)
            .build();
        orderRepository.save(order);
        return order;
    }

    private List<Post> findAll() {
        return postRepository.findAll();
    }

    private List<Post> findPage(int page) {
        Pageable pageWithTenPosts = PageRequest.of(page, 10, Sort.by("modifiedAt").descending());
        Page<Post> postPage = postRepository.findAll(pageWithTenPosts);
        List<Post> posts = postPage.getContent();
        return posts;
    }

    private User getAuthor(List<UserPost> userPosts) {
        for (UserPost userpost : userPosts) {
            if (userpost.getRole().equals(UserPostRole.HOST)) {
                return userpost.getUser();
            }
        }
        throw new GlobalException(UserPostErrorCode.NOT_FOUND_HOST);
    }

    private String getAuthorNick(List<UserPost> userPosts) {
        for (UserPost userpost : userPosts) {
            if (userpost.getRole().equals(UserPostRole.HOST)) {
                return userpost.getUser().getNickname();
            }
        }
        return " ";
    }

    private int getSumPrice(List<UserPost> userposts, Post post) {
        List<Menu> menus = menuRepository.findAllByPost(post);
        return getSumPrice(userposts, menus);
    }

    private int getSumPrice(List<UserPost> userposts, List<Menu> menus) {
        int sumPrice = 0;

        //add all prices from the menus from the users who are participating/hosting a post
        for (Menu menu : menus) {
            for (UserPost userpost : userposts) {
                if (userpost.getUser().getId().equals(menu.getUser().getId())) {
                    sumPrice += menu.getPrice();
                    break;
                }
            }
        }

        return sumPrice;
    }

    private LocalDateTime getDeadline(Post post) {
        //remove nano sencods from the LocalDateTime
        return post.getDeadline().withNano(0);
    }

    private String getDeadline(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        int days = deadline.getDayOfYear() - now.getDayOfYear();
        int hours = deadline.getHour() - now.getHour();
        int mins = deadline.getMinute() - now.getMinute();
        if (mins < 0) {
            hours--;
            mins = 60 + mins;
        }
        if (hours < 0) {
            hours = 0;
        }
        return days + "일 " + hours + "시 " + mins + " 분";
    }

    private Post getPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private List<UserPost> getUserPostsByPost(Post post) {
        return userPostRepository.findAllByPost(post);
    }

    private List<Menu> getUserMenus(User user, Post post) {
        return menuRepository.findAllByUserAndPost(user, post);
    }

    private void checkIfHost(User user, Post post) {
        if (!userPostRepository.existsByUserIdAndPostIdAndRole(user.getId(), post.getId(),
            UserPostRole.HOST)) {
            throw new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_HOST);
        }
    }

    private void checkIfHost(User user, User host) {
        if (!user.getId().equals(host.getId())) {
            throw new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_HOST);
        }
    }

    private UserPost getUserPostByUserIfParticipant(User user, List<UserPost> userPosts) {
        for (UserPost userPost : userPosts) {
            if (userPost.getRole().equals(UserPostRole.HOST)) {
                continue;
            }
            if (user.getId().equals(userPost.getUser().getId())) {
                return userPost;
            }
        }
        throw new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_PARTICIPANT);
    }

    private UserPost getUserPostByUserIfHost(User user, List<UserPost> userPosts) {
        for (UserPost userPost : userPosts) {
            if (userPost.getRole().equals(UserPostRole.PARTICIPANT)) {
                continue;
            }
            if (user.getId().equals(userPost.getUser().getId())) {
                return userPost;
            }
        }
        return null;
    }

    private UserPost getUserPostIfParticipant(User user, Post post) {
        return userPostRepository.findByPostAndUserAndRoleEquals(post, user,
                UserPostRole.PARTICIPANT)
            .orElseThrow(() -> new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_PARTICIPANT));
    }

    private void checkIfOrdered(PostStatusEnum status) {
        if (status == PostStatusEnum.ORDERED
            || status == PostStatusEnum.RECEIVED) {
            throw new GlobalException(PostErrorCode.CANNOT_AFTER_ORDERED);
        }
    }

    private void deleteParticipant(UserPost userPost, User user, Post post) {
        menuRepository.deleteAll(getUserMenus(user, post));
        userPostRepository.delete(userPost);
    }
    @Scheduled(fixedRate = 60000)//executed every 1 min
    public void scheduledDelete() {
        List<Post> posts = findAll();
        List<Post> pastDeadline = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Post post : posts) {
            //delete if the post hasn't been closed and past deadlinetb_user_post
            if (post.getPostStatus() == PostStatusEnum.OPEN) {
                if (post.getDeadline().isBefore(now)) {
                    pastDeadline.add(post);
                    userPostRepository.deleteAll(userPostRepository.findAllByPost(post));
                }
            }
        }
        postRepository.deleteAll(pastDeadline);
    }

    private Consumer<User> publishEventToEachParticipants() {
        return participant -> publisher.publishEvent(
            new Event(participant, NotificationType.MEETING_DELETED));
    }

    private void publishEventToHostAndChagePostStatus(Post post) {
        post.changeAmountGoalStatus(); //게시글의 목표금액 충족상태 변경
        User targetHost = userPostRepository.findByPostIdAndRole(post.getId(), UserPostRole.HOST);

        publisher.publishEvent(new Event(targetHost, NotificationType.AMOUNT_IS_NOT_COLLECTED));
        pushEventService.notifyApplyParticipation(targetHost.getId());
    }
}