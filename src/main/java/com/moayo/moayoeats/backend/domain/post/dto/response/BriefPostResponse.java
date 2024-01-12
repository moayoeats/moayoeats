package com.moayo.moayoeats.backend.domain.post.dto.response;

import java.time.LocalDateTime;

public record BriefPostResponse(
    Long id,
    String author,
    String address,
    String store,
    Integer minPrice,
    Integer sumPrice,
    LocalDateTime deadline
) {

}
