package com.moayo.moayoeats.backend.domain.notification.service.impl;

import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.repository.NotificationRepository;
import com.moayo.moayoeats.backend.domain.notification.service.NotificationService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    //TODO: 알림 목록 조회
    public List<NotificationsResponse> getNotifications(User user) {

        User targetUser = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new GlobalException(
                UserErrorCode.NOT_EXIST_USER));

        List<NotificationsResponse> notificationsRes = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findAllByUserIdOrderByCreatedAtDesc(
            targetUser.getId());
        for (Notification notification : notifications) {
            notificationsRes.add(NotificationsResponse.formWith(notification));
        }

        return notificationsRes;
    }

}
