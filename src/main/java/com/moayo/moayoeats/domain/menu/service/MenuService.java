package com.moayo.moayoeats.domain.menu.service;

import com.moayo.moayoeats.domain.menu.dto.request.MenuDeleteRequest;
import com.moayo.moayoeats.domain.menu.dto.request.MenuRequest;
import com.moayo.moayoeats.domain.user.entity.User;

public interface MenuService {

    /**
     * 메뉴를 생성한다
     * @param menuReq : postId, 메뉴 이름, 메뉴 가격이 들어있는 dto
     * @param user : 메뉴를 생성하는 사용자
     */
    public void createMenu(MenuRequest menuReq, User user);

    /**
     *
     * @param menuDeleteReq : menuId
     * @param user : 메뉴를 삭제하는 사용자
     */
    public void deleteMenu(MenuDeleteRequest menuDeleteReq, User user);

}
