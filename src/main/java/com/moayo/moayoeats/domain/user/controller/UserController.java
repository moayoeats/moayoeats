package com.moayo.moayoeats.domain.user.controller;

import com.moayo.moayoeats.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.domain.user.service.UserService;
import com.moayo.moayoeats.global.dto.ApiResponse;
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
    public void login(
        @RequestBody LoginRequest loginReq,
        HttpServletResponse res
    ) {

    }

}
