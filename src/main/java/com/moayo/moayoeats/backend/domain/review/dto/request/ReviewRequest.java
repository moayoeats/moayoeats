package com.moayo.moayoeats.backend.domain.review.dto.request;

import com.moayo.moayoeats.backend.domain.review.entity.ScoreEnum;

public record ReviewRequest(
    Long orderId,
    ScoreEnum score,
    boolean goodmanner,
    boolean goodtime,
    boolean goodcomm,
    boolean badtime,
    boolean noshow,
    boolean nomoney,
    boolean badcomm,
    boolean badmanner
) {

}
