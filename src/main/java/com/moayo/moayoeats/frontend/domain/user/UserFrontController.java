package com.moayo.moayoeats.frontend.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserFrontController {

    @GetMapping("/login")
    public String loginPage() {
        return "/domain/user/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "/domain/user/signup";
    }
}
