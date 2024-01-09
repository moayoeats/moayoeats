package com.moayo.moayoeats.domain.menu.service;

import com.moayo.moayoeats.domain.menu.dto.request.MenuRequest;

public interface MenuService {

    /**
     * 메뉴를 생성한다
     * @param menuReq : postId, 메뉴 이름, 메뉴 가격이 들어있는 dto
     */
    public void createMenu(MenuRequest menuReq);

}
