package com.moayo.moayoeats.backend.domain.user.dto.request;


import com.moayo.moayoeats.test.CommonTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("회원가입 요청 DTO 유효성 검사")
class SignupRequestTest implements CommonTest {

    @DisplayName("회원가입 요청 DTO 생성")
    @Nested
    class signupReq {
        ValidatorFactory factory;
        Validator validator;

        @BeforeEach
        void setUp() {

            factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @DisplayName("회원가입 요청 DTO 생성 성공")
        @Test
        void createSignupReq_success() {

            // given
            SignupRequest signupReq = new SignupRequest(
                TEST_USER_EMAIL,
                TEST_USER_PASSWORD,
                TEST_USER_CHECK_PASSWORD,
                TEST_USER_NICKNAME
            );

            // when
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupReq);

            // then
            assertThat(violations).isEmpty();
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 email")
        @Test
        void createSignupReq_wrongEmail() {

            // given
            SignupRequest signupReq = new SignupRequest(
                "Invalid email",
                TEST_USER_PASSWORD,
                TEST_USER_CHECK_PASSWORD,
                TEST_USER_NICKNAME
            );

            // when
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupReq);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("email 올바른 형식의 이메일 주소여야 합니다.");
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 password")
        @Test
        void createSignupReq_wrongPassword() {

            // given
            SignupRequest signupReq = new SignupRequest(
                TEST_USER_EMAIL,
                "Invalid password",
                TEST_USER_CHECK_PASSWORD,
                TEST_USER_NICKNAME
            );

            // when
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupReq);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("비밀번호는 a-z, A-Z, 0-9, !@#$ 만 포함하고 8-15자이어야 합니다.");
        }

        @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 nickname")
        @Test
        void createSignupReq_wrongNickname() {

            // given
            SignupRequest signupReq = new SignupRequest(
                TEST_USER_EMAIL,
                TEST_USER_PASSWORD,
                TEST_USER_CHECK_PASSWORD,
                "Invalid nickname"
            );

            // when
            Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupReq);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("닉네임은 a-z, A-Z, 0-9, 한글 만 포함하고 2-20자이어야 합니다.");
        }

    }
}