package com.moayo.moayoeats.domain.notification.service;

import com.moayo.moayoeats.domain.notification.dto.response.*;
import com.moayo.moayoeats.domain.user.entity.*;
import java.util.*;

public interface NotificationService {

    /**
     * @param user
     * @return 해당 유저에 대한 알림들
     */
    List<NotificationsResponse> getNotifications(User user);

}
