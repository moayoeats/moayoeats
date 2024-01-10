package com.moayo.moayoeats.domain.menu.service.impl;

import com.moayo.moayoeats.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.menu.exception.MenuErrorCode;
import com.moayo.moayoeats.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.domain.menu.service.MenuService;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final PostRepository postRepository;

    public void createMenu(MenuRequest menuReq, User user) {

        Post post = findPostById(menuReq.postId());

        Menu menu = Menu.builder().post(post).menuname(menuReq.name()).price(menuReq.price())
            .user(user).build();

        menuRepository.save(menu);
    }

    public void deleteMenu(MenuDeleteRequest menuDeleteReq, User user) {
        Menu menu = findMenuById(menuDeleteReq.menuId(), user);

        menuRepository.delete(menu);
    }

    private Post findPostById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
        return post;
    }

    private Menu findMenuById(Long menuId, User user){
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new GlobalException(MenuErrorCode.NOT_FOUND_MENU));
        if(!menu.getUser().getId().equals(user.getId())){
            throw new GlobalException(MenuErrorCode.FORBIDDEN_ACCESS);
        }
        return menu;
    }

}
