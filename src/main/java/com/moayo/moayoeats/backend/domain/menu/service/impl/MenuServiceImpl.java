package com.moayo.moayoeats.backend.domain.menu.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.menu.exception.MenuErrorCode;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.menu.service.MenuService;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.event.Event;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.pushEvent.PushEventService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final ApplicationEventPublisher publisher;
    private final PushEventService pushEventService;

    @Override
    public void createMenu(MenuRequest menuReq, User user) {

        Post post = findPostById(menuReq.postId());
        checkIfPostIsClosed(post);

        Menu menu = Menu.builder().post(post).menuname(menuReq.name()).price(menuReq.price())
            .user(user).build();

        menuRepository.save(menu);

        int sumPrice = getSumPrice(post);
        //모인금액이 목표가격을 충족하고, 게시글이 목표금액을 달성하지 않은 상태일 때 방장에 알림
        if (sumPrice >= post.getMinPrice() && !post.getAmountIsSatisfied()) {
            publishEventToHostAndChagePostStatus(post);

        }
    }

    @Override
    public void deleteMenu(MenuDeleteRequest menuDeleteReq, User user) {
        Menu menu = findMenuById(menuDeleteReq.menuId(), user);
        checkIfPostIsClosed(menu.getPost());

        menuRepository.delete(menu);

        int sumPrice = getSumPrice(menu.getPost());
        //모인금액이 목표가격의 미만이고, 게시글이 목표금액을 달성한 상태일 때 방장에 알림
        if (sumPrice < menu.getPost().getMinPrice() && menu.getPost().getAmountIsSatisfied()) {
            publishEventToHostAndChagePostStatus(menu.getPost());
        }
    }

    @Override
    public List<MenuResponse> getMenus(Long postId, User user) {
        Post post = findPostById(postId);
        List<Menu> menus = menuRepository.findAllByUserAndPost(user, post);
        return menus.stream()
            .map(menu -> new MenuResponse(menu.getId(), menu.getMenuname(), menu.getPrice()))
            .toList();
    }

    private Post findPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private Menu findMenuById(Long menuId, User user) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new GlobalException(MenuErrorCode.NOT_FOUND_MENU));
        if (!menu.getUser().getId().equals(user.getId())) {
            throw new GlobalException(MenuErrorCode.FORBIDDEN_ACCESS);
        }
        return menu;
    }

    private void checkIfPostIsClosed(Post post) {
        //주문완료 후에는 메뉴를 생성/삭제할 수 없음
        if (post.getPostStatus() == PostStatusEnum.RECEIVED
            || post.getPostStatus() == PostStatusEnum.ORDERED) {
            throw new GlobalException(PostErrorCode.MENU_NOT_ALLOWED);
        }
    }

    private int getSumPrice(Post post) {
        int sumPrice = 0;
        List<UserPost> userPosts = userPostRepository.findAllByPost(post);
        List<Menu> menus = menuRepository.findAllByPost(post);
        //add all prices from the menus from the users who are participating/hosting a post
        for (Menu menu : menus) {
            for (UserPost userPost : userPosts) {
                if (userPost.getUser().getId().equals(menu.getUser().getId())) {
                    sumPrice += menu.getPrice();
                    break;
                }
            }
        }
        return sumPrice;
    }

    private void publishEventToHostAndChagePostStatus(Post post) {
        post.changeAmountGoalStatus(); //게시글의 목표금액 충족상태 변경
        User targetHost = userPostRepository.findByPostIdAndRole(post.getId(),
            UserPostRole.HOST);
        if (post.getAmountIsSatisfied()) {
            publisher.publishEvent(new Event(targetHost, NotificationType.AMOUNT_COLLECTED));
            pushEventService.notifyAmoutCollected(targetHost.getId());
        } else {
            publisher.publishEvent(new Event(targetHost, NotificationType.AMOUNT_IS_NOT_COLLECTED));
            pushEventService.notifyAmoutIsNotCollected(targetHost.getId());
        }
    }

}
