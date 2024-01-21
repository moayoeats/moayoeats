package com.moayo.moayoeats.backend.domain.offer.dto.response;

import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import java.util.List;

public record OfferRoleResponse(
    List<OfferResponse> offerResponses,
    UserPostRole role
) {
}
