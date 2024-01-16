package com.moayo.moayoeats.frontend.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserFrontController {

    @GetMapping("/login-page")
    public String loginPage() {
        return "/domain/user/login";
    }

    @GetMapping("/sign-up-page")
    public String signupPage() {
        return "/domain/user/signup";
    }
}
