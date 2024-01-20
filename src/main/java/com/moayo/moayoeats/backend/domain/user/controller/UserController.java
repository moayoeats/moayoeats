package com.moayo.moayoeats.backend.domain.user.controller;

import com.moayo.moayoeats.backend.domain.user.dto.request.AddressUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.InfoUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.LoginRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.PasswordUpdateRequest;
import com.moayo.moayoeats.backend.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.backend.domain.user.dto.response.MyPageResponse;
import com.moayo.moayoeats.backend.domain.user.dto.response.OtherUserPageResponse;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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
//        res.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        jwtUtil.addJwtToCookie(token, res);

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

    @PatchMapping("/password")
    public ApiResponse<Void> updatePassword(
        @Valid @RequestBody PasswordUpdateRequest passwordUpdateReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        userService.updatePassword(passwordUpdateReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "비밀번호를 수정하였습니다.");
    }

    @GetMapping("/me")
    public ApiResponse<MyPageResponse> openMyPage(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        MyPageResponse myPageRes = userService.openMyPage(userDetails.getUser());
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "마이페이지를 가져왔습니다.",
            myPageRes
        );
    }

    @GetMapping("/profile/{otherUserId}")
    public ApiResponse<OtherUserPageResponse> openOtherUserPage(
        @PathVariable Long otherUserId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        OtherUserPageResponse otherUserPageRes =
            userService.openOtherUserPage(otherUserId, userDetails.getUser());
        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "다른사람 페이지를 가져왔습니다.",
            otherUserPageRes
        );
    }

    @PatchMapping("/address")
    public ApiResponse<Void> updateAddress(
        @Valid @RequestBody AddressUpdateRequest addressUpdateReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        userService.updateAddress(addressUpdateReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "주소를 수정하였습니다.");
    }

}
