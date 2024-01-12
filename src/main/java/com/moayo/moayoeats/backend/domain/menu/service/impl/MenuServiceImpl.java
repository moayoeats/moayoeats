package com.moayo.moayoeats.backend.domain.menu.service.impl;

import com.moayo.moayoeats.backend.domain.menu.exception.MenuErrorCode;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.menu.service.MenuService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final PostRepository postRepository;

    public void createMenu(MenuRequest menuReq, User user) {

        Post post = findPostById(menuReq.postId());
        checkIfPostIsClosed(post);

        Menu menu = Menu.builder().post(post).menuname(menuReq.name()).price(menuReq.price())
            .user(user).build();

        menuRepository.save(menu);
    }

    public void deleteMenu(MenuDeleteRequest menuDeleteReq, User user) {
        Menu menu = findMenuById(menuDeleteReq.menuId(), user);
        checkIfPostIsClosed(menu.getPost());
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
    private void checkIfPostIsClosed(Post post){
        //모집마감 후에는 메뉴를 생성/삭제할 수 없음
        if(post.getPostStatus()!= PostStatusEnum.OPEN){
            throw new GlobalException(PostErrorCode.MENU_NOT_ALLOWED);
        }
    }

}
