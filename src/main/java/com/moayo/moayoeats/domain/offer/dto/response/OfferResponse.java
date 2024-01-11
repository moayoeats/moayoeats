package com.moayo.moayoeats.domain.offer.dto.response;

import lombok.Builder;

@Builder
public record OfferResponse(
    Long postId,
    Long userId,
    String userNickname
) {

}
