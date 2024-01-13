package com.moayo.moayoeats.backend.domain.menu.service;

import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuReadRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface MenuService {

    /**
     * 메뉴를 생성한다
     *
     * @param menuReq : postId, 메뉴 이름, 메뉴 가격이 들어있는 dto
     * @param user    : 메뉴를 생성하는 사용자
     */
    void createMenu(MenuRequest menuReq, User user);

    /**
     * @param menuDeleteReq : menuId
     * @param user          : 메뉴를 삭제하는 사용자
     */
    void deleteMenu(MenuDeleteRequest menuDeleteReq, User user);

    /**
     * @param menuReadReq : postId
     * @param user        : 메뉴 조회자, 본인이 해당 글에 담은 메뉴 조회함
     */
    List<MenuResponse> getMenus(MenuReadRequest menuReadReq, User user);

}
