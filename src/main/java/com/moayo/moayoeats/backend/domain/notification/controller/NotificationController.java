package com.moayo.moayoeats.backend.domain.notification.controller;

import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.notification.service.NotificationService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import com.moayo.moayoeats.backend.domain.notification.dto.response.*;
import com.moayo.moayoeats.backend.domain.notification.service.*;
import java.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    //TODO: 알림 목록 조회
    @GetMapping
    public ApiResponse<List<NotificationsResponse>> getNotifications(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<NotificationsResponse> notificationsRes = notificationService.getNotifications(
            userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "알림 목록을 불러왔습니다!", notificationsRes);
    }

    //TODO: 알림 전체 삭제
}
