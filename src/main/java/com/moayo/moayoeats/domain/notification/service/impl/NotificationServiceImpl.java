package com.moayo.moayoeats.domain.notification.service.impl;

import com.moayo.moayoeats.domain.notification.dto.response.*;
import com.moayo.moayoeats.domain.notification.entity.*;
import com.moayo.moayoeats.domain.notification.repository.*;
import com.moayo.moayoeats.domain.notification.service.*;
import com.moayo.moayoeats.domain.user.entity.*;
import com.moayo.moayoeats.domain.user.exception.*;
import com.moayo.moayoeats.domain.user.repository.*;
import com.moayo.moayoeats.global.exception.*;
import java.util.*;
import lombok.*;
import org.springframework.stereotype.*;

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

        List<Notification> notifications = notificationRepository.findAllByUserId(
            targetUser.getId());
        List<NotificationsResponse> NotificationsRes = new ArrayList<>();
        notifications.sort(((o1, o2) -> o2.getCreatedAt()
            .compareTo(o1.getCreatedAt())));
        for (Notification notification : notifications) {
            NotificationsRes.add(NotificationsResponse.formWith(notification));
        }

        return NotificationsRes;
    }

}
