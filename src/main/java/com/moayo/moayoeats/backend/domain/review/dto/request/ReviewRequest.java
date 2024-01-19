package com.moayo.moayoeats.backend.domain.review.dto.request;

import com.moayo.moayoeats.backend.domain.score.entity.ScoreEnum;

public record ReviewRequest(
    Long orderId,
    ScoreEnum score,
    Integer goodmanner,
    Integer goodtime,
    Integer goodcomm,
    Integer badtime,
    Integer noshow,
    Integer nomoney,
    Integer badcomm,
    Integer badmanner
) {

}
