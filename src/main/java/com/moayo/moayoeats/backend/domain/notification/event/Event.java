package com.moayo.moayoeats.backend.domain.notification.event;

import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import lombok.Builder;

@Builder
public record Event(

    User user,

    NotificationType notificationType
) {

}
