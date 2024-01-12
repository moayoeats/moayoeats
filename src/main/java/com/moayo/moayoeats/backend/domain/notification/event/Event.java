package com.moayo.moayoeats.backend.domain.notification.event;

import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.notification.entity.*;
import com.moayo.moayoeats.backend.domain.user.entity.*;
import lombok.*;

@Builder
public record Event(

    User user,

    NotificationType notificationType
) {

    public static Event formWith(User user, NotificationType type) {
        return Event.builder()
            .user(user)
            .notificationType(type)
            .build();
    }
}