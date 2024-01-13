package com.moayo.moayoeats.backend.domain.notification.controller;

import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.notification.service.NotificationService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
