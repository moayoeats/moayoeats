package com.moayo.moayoeats.test;


import com.moayo.moayoeats.backend.domain.user.entity.User;

public interface UserTest {

    Long TEST_USER_ID = 1L;

    String TEST_USER_NAME = "username";
    String TEST_USER_PASSWORD = "ABcd5678#&";
    String TEST_USER_EMAIL = "username@gmail.com";
    String TEST_USER_INTRODUCE = "introduce";
    String TEST_USER_PROFILE_URL = "resources/images/sparta.png";

    User TEST_USER =
        User.builder()
            .email(TEST_USER_EMAIL)
            .nickname(TEST_USER_NAME)
            .password(TEST_USER_PASSWORD)
            .build();
}
