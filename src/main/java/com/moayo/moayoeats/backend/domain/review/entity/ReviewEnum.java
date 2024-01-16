package com.moayo.moayoeats.backend.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewEnum {
    //positive
    GOODMANNER("친절하고 매너가 좋아요"),
    GOODTIME("시간 약속을 잘 지켜요"),
    GOODCOMM("소통과 응답이 빨라요"),
    //negative
    BADTIME("약속 시간에 나타나지 않았어요"),
    NOSHOW("아예 나타나지 않았어요"),
    NOMONEY("값을 지불하지 않았어요"),
    BADCOMM("소통과 응답이 느려요"),
    BADMANNER("불친절하고 매너가 좋지 않아요");

    private final String comment;
}
