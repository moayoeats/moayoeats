package com.moayo.moayoeats.domain.menu.controller;

import com.moayo.moayoeats.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.domain.menu.service.MenuService;
import com.moayo.moayoeats.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;

    // 글에 본인이 주문할 Menu 추가하기
    @PostMapping("/menus")
    public ApiResponse<Void> createMenu(
        @Valid @RequestBody MenuRequest menuReq
    ){
        menuService.createMenu(menuReq);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "메뉴를 추가했습니다.");
    }

}
