package com.moayo.moayoeats.backend.domain.post.service.impl;

import com.moayo.moayoeats.backend.domain.chat.service.ChatRoomService;
import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.dto.response.NickMenusResponse;
import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.event.Event;
import com.moayo.moayoeats.backend.domain.order.entity.Order;
import com.moayo.moayoeats.backend.domain.order.repository.OrderRepository;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostCategoryRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.backend.domain.post.dto.request.PostSearchRequest;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.post.service.PostService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;//Test
    private final ChatRoomService chatRoomService;


    @Override
    public void createPost(PostRequest postReq, User user) {
        //set deadline to hours and mins after now
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(postReq.deadlineMins())
            .plusHours(postReq.deadlineHours());

        //Build new post with the post request dto
        Post post = Post.builder().address(postReq.address()).store(postReq.store())
            .deliveryCost(postReq.deliveryCost()).minPrice(postReq.minPrice()).deadline(deadline)
            .category(postReq.category()).postStatus(PostStatusEnum.OPEN).build();

        //save the post
        postRepository.save(post);

        //create chatRoom
        chatRoomService.createRoom(post.getId());

        //Build new relation between the post and the user
        UserPost userpost = UserPost.builder().user(user).post(post).role(UserPostRole.HOST)
            .build();

        //save the relation
        userPostRepository.save(userpost);
    }

    @Override
    public List<BriefPostResponse> getPosts(User user) {
        List<Post> posts = findAll();
        return postsToBriefResponses(posts);
    }

    @Override
    public DetailedPostResponse getPost(Long postId, User user) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);

        return DetailedPostResponse.builder()
            .longitude(post.getLongitude())
            .latitude(post.getLatitude())
            .address(post.getAddress())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .menus(getNickMenus(userPosts))
            .sumPrice(getSumPrice(userPosts, post))
            .deadline(getDeadline(post))
            .build();
    }

    @Override
    public List<BriefPostResponse> getPostsByCategory(PostCategoryRequest postCategoryReq,
        User user) {
        List<Post> posts;
        if (postCategoryReq.category().equals(CategoryEnum.ALL.toString())) {
            posts = findAll();
        } else {
            posts = postRepository.findAllByCategoryEquals(postCategoryReq.category()).orElse(null);
        }
        return postsToBriefResponses(posts);
    }

    @Override
    public List<BriefPostResponse> searchPost(PostSearchRequest postSearchReq, User user) {
        //get all posts filtered by search keyword
        List<Post> posts = postRepository.findPostByStoreContaining(postSearchReq.keyword())
            .orElse(null);
        //List<Post> -> List<BriefPostResponse>
        return postsToBriefResponses(posts);
    }

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
        if(post.getPostStatus()==PostStatusEnum.ORDERED||post.getPostStatus()==PostStatusEnum.RECEIVED){
            throw new GlobalException(PostErrorCode.CANNOT_CLOSE_AFTER_ORDERED);
        }

        //참가자들에게 알림
        userPosts.stream()
            .filter(userPost -> userPost.getRole().equals(UserPostRole.PARTICIPANT))
            .map(UserPost::getUser)
            .forEach(publishEventToEachParticipants());

        userPostRepository.deleteAll(userPosts);
        chatRoomService.deleteRoom(post.getId());
        postRepository.delete(post);
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

        //참가자들에게 알림
        userPostRepository.findAllByPostAndRoleEquals(post, UserPostRole.PARTICIPANT)
            .stream()
            .map(UserPost::getUser)
            .forEach(publishEventToEachParticipants());

        post.closeApplication();
        postRepository.save(post);
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
        menuRepository.deleteAll(getUserMenus(user, post));
        userPostRepository.delete(userPost);
    }

    @Transactional
    @Override
    public void receiveOrder(PostIdRequest postIdReq, User user) {
        Post post = getPostById(postIdReq.postId());

        //get relations
        List<UserPost> userPosts = getUserPostsByPost(post);
        UserPost userpost = getUserPostByUserIfParticipant(user, userPosts);
        User host = getAuthor(userPosts);

        //make order for me to review
        Order order = makeOrder(post, host, user, UserPostRole.PARTICIPANT);
        orderRepository.save(order);
        //make order for host to review
        Order hostOrder = makeOrder(post, user, host, UserPostRole.HOST);
        orderRepository.save(hostOrder);

        //remove menus from the post
        List<Menu> menus = getUserMenus(user, post);
        menus.forEach(menu -> menuRepository.save(menu.receive(order)));

        if (userPosts.size() <= 2) {
            userPostRepository.deleteAll(userPosts);
            postRepository.delete(post);
            return;
        }
        userPostRepository.delete(userpost);

    }

    private Order makeOrder(Post post, User receiver, User user, UserPostRole role) {
        return Order.builder()
            .receiver(receiver)
            .user(user)
            .store(post.getStore())
            .senderRole(role)
            .build();
    }

    private List<Post> findAll() {
        return postRepository.findAll();
    }

    private List<BriefPostResponse> postsToBriefResponses(List<Post> posts) {
        return posts.stream().map((Post post) -> new BriefPostResponse(post.getId(),
                getAuthor(getUserPostsByPost(post)).getNickname(), post.getAddress(), post.getStore(),
                post.getMinPrice(), getSumPrice(getUserPostsByPost(post), post), getDeadline(post)))
            .toList();
    }

    private User getAuthor(List<UserPost> userPosts) {
        for (UserPost userpost : userPosts) {
            if (userpost.getRole().equals(UserPostRole.HOST)) {
                return userpost.getUser();
            }
        }
        throw new GlobalException(UserPostErrorCode.NOT_FOUND_HOST);
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

    private Post getPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private List<UserPost> getUserPostsByPost(Post post) {
        return userPostRepository.findAllByPost(post);
    }

    private List<NickMenusResponse> getNickMenus(List<UserPost> userposts) {

        List<NickMenusResponse> menus =
            //List<UserPost> -> List<NickMenusResponse>
            userposts.stream()
                .map((UserPost userpost) -> new NickMenusResponse(userpost.getUser().getNickname(),
                    //List<Menu> menus -> List<MenuResponse>
                    getUserMenus(userpost.getUser(), userpost.getPost()).stream()
                        .map((Menu menu) -> new MenuResponse(menu.getMenuname(), menu.getPrice()))
                        .toList())).toList();
        return menus;
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

    private UserPost getUserPostIfParticipant(User user, Post post) {
        return userPostRepository.findByPostAndUserAndRoleEquals(post, user,
            UserPostRole.PARTICIPANT).orElseThrow(() ->
            new GlobalException(PostErrorCode.FORBIDDEN_ACCESS_PARTICIPANT)
        );
    }

    //Test
    public void createPostTest(PostRequest postReq) {
        //set fake user
        Long l = 1L;
        User user = userRepository.findById(l).orElse(null);

        //set deadline to hours and mins after now
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(postReq.deadlineMins())
            .plusHours(postReq.deadlineHours());

        //get latitude and longitude from the coordinate
        String address = postReq.address();
        address = address.replace("(lat:", "");
        address = address.replace("lng:", "");
        address = address.replace(")", "");
        String[] location = address.split(",");
        double latitude = Double.valueOf(location[0]);
        double longitude = Double.valueOf(location[1]);

        //Build new post with the post request dto
        Post post = Post.builder()
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .store(postReq.store())
            .deliveryCost(postReq.deliveryCost())
            .minPrice(postReq.minPrice())
            .deadline(deadline)
            .category(postReq.category())
            .postStatus(PostStatusEnum.OPEN)
            .build();

        //save the post
        postRepository.save(post);

        //Build new relation between the post and the user
        UserPost userpost = UserPost.builder()
            .user(user)
            .post(post)
            .role(UserPostRole.HOST)
            .build();

        //save the relation
        userPostRepository.save(userpost);
    }

    @Override
    public DetailedPostResponse getPostTest(Long postId) {
        Post post = getPostById(postId);
        List<UserPost> userPosts = getUserPostsByPost(post);

        return DetailedPostResponse.builder()
            .longitude(post.getLongitude())
            .latitude(post.getLatitude())
            .address(post.getAddress())
            .store(post.getStore())
            .minPrice(post.getMinPrice())
            .deliveryCost(post.getDeliveryCost())
            .menus(getNickMenus(userPosts))
            .sumPrice(getSumPrice(userPosts, post))
            .deadline(getDeadline(post))
            .build();
    }

    private Consumer<User> publishEventToEachParticipants() {
        return participant -> publisher.publishEvent(
            new Event(participant, NotificationType.MEETING_DELETED));
    }

}
