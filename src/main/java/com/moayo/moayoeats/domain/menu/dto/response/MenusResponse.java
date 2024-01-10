package com.moayo.moayoeats.domain.menu.dto.response;

import java.util.List;

public record MenusResponse(
    String nickname,
    List<MenuResponse> menus
) {
}
