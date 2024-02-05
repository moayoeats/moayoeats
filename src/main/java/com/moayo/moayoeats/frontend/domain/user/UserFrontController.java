package com.moayo.moayoeats.frontend.domain.user;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserFrontController {

    @GetMapping("/login")
    public String loginPage() {
        return "domain/user/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "domain/user/signup";
    }

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/user/mypage";
    }

    @GetMapping("/user/{otherUserId}")
    public String otherUserPage(@PathVariable Long otherUserId,
        @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        model.addAttribute("otherUserId", otherUserId);
        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/user/other-user-page";
    }

    @GetMapping("/mypage/nickname")
    public String updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model) {

        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/user/update-nickname";
    }

    @GetMapping("/mypage/password")
    public String updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model) {

        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/user/update-password";
    }

    @GetMapping("/mypage/address")
    public String updateAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/user/update-address";
    }
}
