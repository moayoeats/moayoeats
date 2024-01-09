package com.moayo.moayoeats.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryEnum {

    BURGER("햄버거"),
    CHICKEN("치킨"),
    PIZZA("피자"),
    KOREAN("한식"),
    SNACK("분식"),
    WESTERN("양식"),
    ASIAN("아시안"),
    JAPANESE("일식"),
    CHINESE("중식"),
    LUNCHBOX("도시락");

    private final String category;

}
