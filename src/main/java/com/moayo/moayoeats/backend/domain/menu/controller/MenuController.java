package com.moayo.moayoeats.backend.domain.menu.controller;

import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuReadRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.service.MenuService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ApiResponse<Void> createMenu(@Valid @RequestBody MenuRequest menuReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.createMenu(menuReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "메뉴를 추가했습니다.");
    }

    // 글에 본인이 주문할 Menu 삭제하기
    @DeleteMapping("/menus")
    public ApiResponse<Void> deleteMenu(@Valid @RequestBody MenuDeleteRequest menuDeleteReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.deleteMenu(menuDeleteReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "메뉴를 삭제했습니다.");
    }

    //자신의 메뉴 조회
    @GetMapping("/menus")
    ApiResponse<List<MenuResponse>> getMenus(@Valid @RequestBody MenuReadRequest menuReadReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(HttpStatus.OK.value(), "나의 메뉴 조회",
            menuService.getMenus(menuReadReq, userDetails.getUser()));
    }

}
