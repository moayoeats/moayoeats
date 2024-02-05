package com.moayo.moayoeats.frontend.domain.notification;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotificationFrontController {

    @GetMapping("/notification")
    public String notificationPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model) {

        model.addAttribute("userId", userDetails.getUser().getId());

        return "domain/notification/notification";
    }
}
