package com.moayo.moayoeats.backend.domain.menu.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuReadRequest;
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
import com.moayo.moayoeats.backend.domain.user.entity.User;
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

    @Override
    public void createMenu(MenuRequest menuReq, User user) {

        Post post = findPostById(menuReq.postId());
        checkIfPostIsClosed(post);

        Menu menu = Menu.builder().post(post).menuname(menuReq.name()).price(menuReq.price())
            .user(user).build();

        menuRepository.save(menu);

        //모인금액이 목표가격 이상일 시, 방장에 알림
        int sumPrice = getSumPrice(post);
        if (sumPrice >= post.getMinPrice() && !post.getAmountIsSatisfied()) {
            post.changeAmountGoalStatus(); //포스트의 amountIsSatisfied를 true로 변경
            User targetHost = userPostRepository.findByPostIdAndRole(post.getId(),
                UserPostRole.HOST);
            publisher.publishEvent(new Event(targetHost, NotificationType.AMOUNT_COLLECTED));
        }
    }

    @Override
    public void deleteMenu(MenuDeleteRequest menuDeleteReq, User user) {
        Menu menu = findMenuById(menuDeleteReq.menuId(), user);
        checkIfPostIsClosed(menu.getPost());
        menuRepository.delete(menu);
    }

    @Override
    public List<MenuResponse> getMenus(MenuReadRequest menuReadReq, User user) {
        Post post = findPostById(menuReadReq.postId());
        List<Menu> menus = menuRepository.findAllByUserAndPost(user, post);
        return menus.stream().map(menu -> new MenuResponse(menu.getMenuname(), menu.getPrice()))
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
        //모집마감 후에는 메뉴를 생성/삭제할 수 없음
        if (post.getPostStatus() != PostStatusEnum.OPEN) {
            throw new GlobalException(PostErrorCode.MENU_NOT_ALLOWED);
        }
    }

    private int getSumPrice(Post post) {
        int sumPrice = 0;

        //add all prices from the menus from the users who are participating/hosting a post
        List<Menu> menus = menuRepository.findAllByPost(post);
        for (Menu m : menus) {
            sumPrice += m.getPrice();
        }
        return sumPrice;
    }

}
