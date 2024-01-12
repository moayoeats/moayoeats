package com.moayo.moayoeats.backend.domain.notification.service;

import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface NotificationService {

    /**
     * @param user
     * @return 해당 유저에 대한 알림들
     */
    List<NotificationsResponse> getNotifications(User user);

}
