package com.moayo.moayoeats.domain.user.controller;

import com.moayo.moayoeats.domain.user.dto.request.SignupRequest;
import com.moayo.moayoeats.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public void signup(@Valid @RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
    }
}
