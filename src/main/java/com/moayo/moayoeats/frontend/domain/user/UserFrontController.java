package com.moayo.moayoeats.frontend.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/mypage")
    public String myPage() {
        return "/domain/user/mypage";
    }

    @GetMapping("/user/{otherUserId}")
    public String otherUserPage(@PathVariable Long otherUserId, Model model) {

        model.addAttribute("otherUserId", otherUserId);

        return "/domain/user/other-user-page";
    }

    @GetMapping("/mypage/nickname")
    public String updateNickname() {
        return "/domain/user/update-nickname";
    }

    @GetMapping("/mypage/password")
    public String updatePassword() {
        return "/domain/user/update-password";
    }
}
