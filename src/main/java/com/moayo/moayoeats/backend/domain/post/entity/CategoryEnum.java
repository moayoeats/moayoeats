package com.moayo.moayoeats.backend.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryEnum {

    ALL,
    BURGER,
    CHICKEN,
    PIZZA,
    KOREAN,
    SNACK,
    WESTERN,
    ASIAN,
    JAPANESE,
    CHINESE,
    LUNCHBOX;

}
