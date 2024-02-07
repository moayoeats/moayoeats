package com.moayo.moayoeats.test;


import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;

public interface UserTest extends CommonTest {

    String TEST_USER_INTRODUCE = "introduce";
    String TEST_USER_PROFILE_URL = "resources/images/sparta.png";

    String TEST_WRONG_USER_PASSWORD = "WrongPassword123!";

    SignupRequest TEST_SIGNUP_REQ = SignupRequest.builder()
        .email(TEST_USER_EMAIL)
        .password(TEST_USER_PASSWORD)
        .checkPassword(TEST_USER_CHECK_PASSWORD)
        .nickname(TEST_USER_NICKNAME)
        .build();
}
