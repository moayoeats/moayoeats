package com.moayo.moayoeats.backend.domain.offer.dto.response;

import lombok.Builder;

@Builder
public record OfferResponse(
    Long postId,
    Long userId,
    String userNickname
) {

}
