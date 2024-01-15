package com.moayo.moayoeats.backend.domain.user.controller;

import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.service.UserService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
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


    @PatchMapping()
    public ApiResponse<Void> updateInfo(
        @Valid @RequestBody InfoUpdateRequest infoUpdateReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        userService.updateInfo(infoUpdateReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "회원정보를 수정하였습니다.");
    }

}
