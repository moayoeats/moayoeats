package com.moayo.moayoeats.backend.domain.user.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import com.moayo.moayoeats.test.CommonTest;
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
class UserServiceImplTest implements CommonTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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

            // When
            userService.updateInfo(infoUpdateReq, TEST_USER);

            // Then
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

            // When
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updateInfo(infoUpdateReq, TEST_USER));

            // Then
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

            // When
            GlobalException exception = assertThrows(GlobalException.class,
                () -> userService.updateInfo(infoUpdateReq, TEST_USER));

            // Then
            assertThat(exception.getErrorCode().getMessage())
                .isEqualTo(UserErrorCode.ALREADY_EXIST_USER_NICKNAME.getMessage());
        }
    }
}