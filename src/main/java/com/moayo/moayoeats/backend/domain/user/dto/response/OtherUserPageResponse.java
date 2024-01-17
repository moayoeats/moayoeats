package com.moayo.moayoeats.backend.domain.user.dto.response;

import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import lombok.Builder;

@Builder
public record OtherUserPageResponse(
    String nickname,
    Double score,
    ReviewResponse reviews
) {

}
