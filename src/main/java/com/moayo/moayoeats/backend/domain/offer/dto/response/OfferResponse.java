package com.moayo.moayoeats.backend.domain.offer.dto.response;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record OfferResponse(
    Long offerId,
    String nickname,
    List<MenuResponse> menus
) {

}
