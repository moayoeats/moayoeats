package com.moayo.moayoeats.test;


import static com.moayo.moayoeats.test.OrderTest.TEST_ORDERS;
import static com.moayo.moayoeats.test.ReviewTest.TEST_REVIEW_RES;
import static com.moayo.moayoeats.test.ReviewTest.TEST_USER_SCORE;

import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.dto.response.MyPageResponse;
import com.moayo.moayoeats.backend.domain.user.dto.response.OtherUserPageResponse;

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

    InfoUpdateRequest TEST_UPDATE_INFO_REQ = InfoUpdateRequest.builder()
        .nickname(TEST_ANOTHER_USER_NICKNAME)
        .password(TEST_USER_PASSWORD)
        .build();

    MyPageResponse TEST_MYPAGE_RES = MyPageResponse.builder()
        .nickname(TEST_USER_NICKNAME)
        .email(TEST_USER_EMAIL)
        .score(TEST_USER_SCORE)
        .reviews(TEST_REVIEW_RES)
        .pastOrderList(TEST_ORDERS)
        .build();

    OtherUserPageResponse TEST_ANOTHER_USER_PAGE_RES = OtherUserPageResponse.builder()
        .nickname(TEST_ANOTHER_USER_NICKNAME)
        .score(TEST_USER_SCORE)
        .reviews(TEST_REVIEW_RES)
        .build();
}
