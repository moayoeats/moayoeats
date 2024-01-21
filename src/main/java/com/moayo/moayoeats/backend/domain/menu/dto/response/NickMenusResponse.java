package com.moayo.moayoeats.backend.domain.menu.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NickMenusResponse(
    String nickname,
    List<MenuResponse> menus
) {

}
