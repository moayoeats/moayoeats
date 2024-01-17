package com.moayo.moayoeats.backend.domain.user.dto.response;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record MyPageResponse(
    String nickname,
    String email,
    Double score,
    ReviewResponse reviews,
    List<OrderResponse> pastOrderList
) {

}
