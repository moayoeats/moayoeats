package com.moayo.moayoeats.test;

import com.moayo.moayoeats.backend.domain.user.entity.User;

public interface CommonTest {

    Long TEST_USER_ID = 1L;

    String TEST_USER_EMAIL = "test00@email.com";
    String TEST_USER_PASSWORD = "Password123!";
    String TEST_USER_CHECK_PASSWORD = "Password123!";
    String TEST_USER_NICKNAME = "사용자1";

    String TEST_ANOTHER_USER_NICKNAME = "사용자1변경";

    User TEST_USER = User.builder()
        .email(TEST_USER_EMAIL)
        .password(TEST_USER_PASSWORD)
        .nickname(TEST_USER_NICKNAME)
        .build();

    String TEST_ANOTHER_USER_PASSWORD = "PASSpass123!";

    String TEST_WRONG_USER_PASSWORD = "WrongPassword123!";
}
