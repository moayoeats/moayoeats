package com.moayo.moayoeats.domain.menu.controller;

import com.moayo.moayoeats.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuController {

    private final MenuService menuService;

}
