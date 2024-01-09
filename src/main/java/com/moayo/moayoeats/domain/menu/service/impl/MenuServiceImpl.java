package com.moayo.moayoeats.domain.menu.service.impl;

import com.moayo.moayoeats.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.domain.menu.entity.Menu;
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

    public void createMenu(MenuRequest menuReq, User user){

        Post post = postRepository.findById(menuReq.postId()).orElseThrow(()->
            new GlobalException(PostErrorCode.NOT_FOUND_POST));

        Menu menu = Menu.builder()
            .post(post)
            .menuname(menuReq.name())
            .price(menuReq.price())
            .user(user)
            .build();

        menuRepository.save(menu);
    }

}
