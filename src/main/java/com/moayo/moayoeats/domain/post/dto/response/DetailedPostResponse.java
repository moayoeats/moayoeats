package com.moayo.moayoeats.domain.post.dto.response;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record DetailedPostResponse (
    String address,
    String store,
    Integer minPrice,
    Integer deliveryCost,
    List<String> participants,
    List<List<Menu>> menus,
    List<Menu> myMenus,
    Integer sumPrice,
    LocalDateTime deadline
){
}
