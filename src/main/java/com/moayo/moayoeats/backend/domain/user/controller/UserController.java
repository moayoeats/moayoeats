package com.moayo.moayoeats.backend.domain.user.controller;

import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.service.UserService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<Void> signup(
        @Valid @RequestBody SignupRequest signupReq
    ) {

        userService.signup(signupReq);
        return new ApiResponse<>(HttpStatus.CREATED.value(), "회원가입을 성공했습니다.");
    }

    @PostMapping("/login")
    public ApiResponse<Void> login(
        @RequestBody LoginRequest loginReq,
        HttpServletResponse res
    ) {

        String token = userService.login(loginReq);
        res.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return new ApiResponse<>(HttpStatus.OK.value(), "로그인을 성공했습니다.");
    }

}
