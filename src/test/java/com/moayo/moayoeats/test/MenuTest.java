package com.moayo.moayoeats.test;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import java.util.ArrayList;
import java.util.List;

public interface MenuTest extends CommonTest {

    Long TEST_MENU_ID = 1L;
    String TEST_MENU_NAME = "menu";
    Integer TEST_MENU_PRICE = 30000;

    MenuResponse TEST_MENU_RES =
        MenuResponse.builder()
            .id(TEST_MENU_ID)
            .menuname(TEST_MENU_NAME)
            .price(TEST_MENU_PRICE)
            .build();

    List<MenuResponse> TEST_MENUS = new ArrayList<>() {
        {
            add(TEST_MENU_RES);
        }
    };
}