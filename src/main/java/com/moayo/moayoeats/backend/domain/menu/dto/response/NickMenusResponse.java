package com.moayo.moayoeats.backend.domain.menu.dto.response;

import java.util.List;

public record NickMenusResponse(
    String nickname,
    List<MenuResponse> menus
) {
}
