package com.moayo.moayoeats.backend.domain.review.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record ReviewResponse(
    Map<String, Integer> reviews

) {

}
