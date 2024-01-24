package com.moayo.moayoeats.backend.domain.post.dto.response;

import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BriefPostResponse(
    Long id,
    String author,
    String store,
    Integer minPrice,
    Integer sumPrice,
    LocalDateTime deadline,
    PostStatusEnum status

) {

}
