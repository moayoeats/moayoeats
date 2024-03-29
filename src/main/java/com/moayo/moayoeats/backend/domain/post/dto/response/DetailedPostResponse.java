package com.moayo.moayoeats.backend.domain.post.dto.response;

import com.moayo.moayoeats.backend.domain.menu.dto.response.NickMenusResponse;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record DetailedPostResponse(

    Long id,
    Long hostId,
    String hostNick,
    Double latitude,
    Double longitude,
    String store,
    Integer minPrice,
    Integer deliveryCost,
    List<NickMenusResponse> menus,
    Integer sumPrice,
    String deadline,
    UserPostRole role,
    PostStatusEnum status
) {

}