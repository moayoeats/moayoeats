package com.moayo.moayoeats.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeadlineEnum {

    M15(15),
    M30(30),
    M45(45),
    M60(60),
    H1M30(90),
    H2(120);

    private final int mins;

}
