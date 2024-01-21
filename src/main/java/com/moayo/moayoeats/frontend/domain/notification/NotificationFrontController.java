package com.moayo.moayoeats.frontend.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotificationFrontController {
    @GetMapping("/notification")
    public String notificationPage(

    ) {
        return "domain/notification/notification";
    }
}
