package com.moayo.moayoeats.backend.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.moayo.moayoeats.backend.domain.BaseMvcTest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.domain.user.service.impl.UserServiceImpl;
import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import com.moayo.moayoeats.test.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("사용자 컨트롤러 테스트")
@WebMvcTest(UserController.class)
class UserControllerTest extends BaseMvcTest implements UserTest {

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtUtil jwtUtil;

    @DisplayName("회원가입 요청")
    @Test
    void signup() throws Exception {

        // given

        // when
        ResultActions action = mockMvc.perform(post("/api/v1/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_SIGNUP_REQ)));

        // then
        action.andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
            .andExpect(jsonPath("$.message")
                .value("회원가입을 성공했습니다."));

        verify(userService, times(1)).signup(any(SignupRequest.class));
    }

    @DisplayName("닉네임 수정 요청")
    @Test
    void updateInfo() throws Exception {

        // given

        // when
        ResultActions action = mockMvc.perform(patch("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .principal(mockPrincipal)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_UPDATE_INFO_REQ)));

        // then
        action.andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message")
                .value("회원정보를 수정하였습니다."));

    }

    @DisplayName("마이페이지 조회 요청")
    @Test
    void openMyPage() throws Exception {

        // given
        given(userService.openMyPage(any(User.class)))
            .willReturn(TEST_MYPAGE_RES);

        // when
        ResultActions action = mockMvc.perform(get("/api/v1/users/me")
            .contentType(MediaType.APPLICATION_JSON)
            .principal(mockPrincipal)
            .accept(MediaType.APPLICATION_JSON));

        // then
        action
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message")
                .value("마이페이지를 가져왔습니다."));
    }
}