package com.moayo.moayoeats.domain.notification.controller;

import com.moayo.moayoeats.domain.notification.dto.response.*;
import com.moayo.moayoeats.domain.notification.service.*;
import com.moayo.moayoeats.global.dto.*;
import com.moayo.moayoeats.global.security.*;
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
