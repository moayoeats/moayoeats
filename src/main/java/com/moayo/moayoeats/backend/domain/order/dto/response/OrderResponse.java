package com.moayo.moayoeats.backend.domain.order.dto.response;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderResponse (
    Long id,
    String store,
    String receiverName,
    List<MenuResponse> menus

){

}
