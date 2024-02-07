package com.moayo.moayoeats.backend.domain.user.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.PasswordUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import com.moayo.moayoeats.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest implements UserTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입")
    @Nested
    class signup {

        @DisplayName("회원가입 - 성공")
        @Test
        void signup_success() {

            // given
            SignupRequest signupReq =
                SignupRequest.builder()
                    .email(TEST_USER_EMAIL)
                    .password(TEST_USER_PASSWORD)
                    .checkPassword(TEST_USER_CHECK_PASSWORD)
                    .nickname(TEST_USER_NICKNAME)
                    .build();

            given(userRepository.existsByEmail(TEST_USER_EMAIL)).willReturn(false);
            given(userRepository.existsByNickname(TEST_USER_NICKNAME)).willReturn(false);
            given(passwordEncoder.matches(eq(TEST_USER_PASSWORD), any())).willReturn(true);

            // when
            userService.signup(signupReq);

            // then
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1)).encode(TEST_USER_PASSWORD);
        }

        @DisplayName("회원가입 - email 중복으로 회원가입 실패")
        @Test
        void signup_fail_emailDuplicate() {

            // given
            SignupRequest signupReq =
                SignupRequest.builder()
                    .email(TEST_USER_EMAIL)
                    .password(TEST_USER_PASSWORD)
                    .checkPassword(TEST_USER_CHECK_PASSWORD)
                    .nickname(TEST_USER_NICKNAME)
                    .build();

            given(userRepository.existsByEmail(any())).willReturn(true);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.signup(signupReq));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.ALREADY_EXIST_USER.getMessage());
        }

        @DisplayName("회원가입 - 비밀번호 불일치로 회원가입 실패")
        @Test
        void signup_fail_passwordMismatch() {

            // given
            SignupRequest signupReq =
                SignupRequest.builder()
                    .email(TEST_USER_EMAIL)
                    .password(TEST_USER_PASSWORD)
                    .checkPassword(TEST_USER_CHECK_PASSWORD)
                    .nickname(TEST_USER_NICKNAME)
                    .build();

            given(userRepository.existsByEmail(TEST_USER_EMAIL)).willReturn(false);
            given(userRepository.existsByNickname(TEST_USER_NICKNAME)).willReturn(false);
            given(passwordEncoder.matches(any(), any())).willReturn(false);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.signup(signupReq));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD.getMessage());
        }

        @DisplayName("회원가입 - nickname 중복으로 회원가입 실패")
        @Test
        void signup_fail_nicknameDuplicate() {

            // given
            SignupRequest signupReq =
                SignupRequest.builder()
                    .email(TEST_USER_EMAIL)
                    .password(TEST_USER_PASSWORD)
                    .checkPassword(TEST_USER_CHECK_PASSWORD)
                    .nickname(TEST_USER_NICKNAME)
                    .build();

            given(userRepository.existsByEmail(TEST_USER_EMAIL)).willReturn(false);
            given(userRepository.existsByNickname(any())).willReturn(true);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.signup(signupReq));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.ALREADY_EXIST_USER_NICKNAME.getMessage());
        }
    }

    @DisplayName("닉네임 수정")
    @Nested
    class updateInfo {

        @DisplayName("닉네임 수정 - 성공")
        @Test
        void updateInfo_success() {

            // given
            InfoUpdateRequest infoUpdateReq = InfoUpdateRequest.builder()
                .nickname(TEST_ANOTHER_USER_NICKNAME)
                .password(TEST_USER_PASSWORD)
                .build();

            given(passwordEncoder.matches(eq(TEST_USER_PASSWORD), any())).willReturn(true);
            given(userRepository.existsByNickname(TEST_ANOTHER_USER_NICKNAME)).willReturn(false);

            // when
            userService.updateInfo(infoUpdateReq, TEST_USER);

            // then
            verify(userRepository, times(1)).save(any(User.class));
            assertThat(TEST_USER.getNickname()).isEqualTo(infoUpdateReq.nickname());
        }

        @DisplayName("닉네임 수정 - 비밀번호 불일치로 업데이트 실패")
        @Test
        void updateInfo_fail_passwordMismatch() {

            // given
            InfoUpdateRequest infoUpdateReq = InfoUpdateRequest.builder()
                .nickname(TEST_ANOTHER_USER_NICKNAME)
                .password(TEST_ANOTHER_USER_PASSWORD)
                .build();

            given(passwordEncoder.matches(any(), any())).willReturn(false);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updateInfo(infoUpdateReq, TEST_USER));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD.getMessage());
        }

        @DisplayName("닉네임 수정 - 닉네임 중복으로 업데이트 실패")
        @Test
        void updateInfo_fail_nicknameDuplicate() {

            // given
            InfoUpdateRequest infoUpdateReq = InfoUpdateRequest.builder()
                .nickname(TEST_ANOTHER_USER_NICKNAME)
                .password(TEST_USER_PASSWORD)
                .build();

            given(passwordEncoder.matches(eq(TEST_USER_PASSWORD), any())).willReturn(true);
            given(userRepository.existsByNickname(any())).willReturn(true);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updateInfo(infoUpdateReq, TEST_USER));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.ALREADY_EXIST_USER_NICKNAME.getMessage());
        }
    }

    @DisplayName("비밀번호 수정")
    @Nested
    class updatePassword {

        @DisplayName("비밀번호 수정 - 성공")
        @Test
        void updatePassword_success() {

            // given
            PasswordUpdateRequest passwordUpdateReq = PasswordUpdateRequest.builder()
                .newPassword(TEST_ANOTHER_USER_PASSWORD)
                .checkPassword(TEST_ANOTHER_USER_PASSWORD)
                .password(TEST_USER_PASSWORD)
                .build();

            given(passwordEncoder.matches(eq(TEST_USER_PASSWORD), any())).willReturn(true);
            given(passwordEncoder.matches(
                eq(TEST_ANOTHER_USER_PASSWORD), any()))
                .willReturn(true);

            // when
            userService.updatePassword(passwordUpdateReq, TEST_USER);

            // then
            verify(userRepository, times(1)).save(any(User.class));
            verify(passwordEncoder, times(1))
                .encode(TEST_ANOTHER_USER_PASSWORD);
        }

        @DisplayName("비밀번호 수정 - 기존 비밀번호 불일치로 업데이트 실패")
        @Test
        void updatePassword_passwordMismatch() {

            // given
            PasswordUpdateRequest passwordUpdateReq = PasswordUpdateRequest.builder()
                .newPassword(TEST_ANOTHER_USER_PASSWORD)
                .checkPassword(TEST_ANOTHER_USER_PASSWORD)
                .password(TEST_WRONG_USER_PASSWORD)
                .build();

            given(passwordEncoder.matches(any(), any())).willReturn(false);

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updatePassword(passwordUpdateReq, TEST_USER));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD.getMessage());
        }

        @DisplayName("비밀번호 수정 - 새로운 비밀번호 불일치로 업데이트 실패")
        @Test
        void updatePassword_newPasswordMismatch() {

            // given
            PasswordUpdateRequest passwordUpdateReq = PasswordUpdateRequest.builder()
                .newPassword(TEST_ANOTHER_USER_PASSWORD)
                .checkPassword(TEST_WRONG_USER_PASSWORD)
                .password(TEST_USER_PASSWORD)
                .build();

            // when
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updatePassword(passwordUpdateReq, TEST_USER));

            // then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.NOT_MATCH_PASSWORD.getMessage());
        }
    }
}