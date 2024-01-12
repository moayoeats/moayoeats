package com.moayo.moayoeats.domain.notification.event;

import com.moayo.moayoeats.domain.notification.entity.*;
import com.moayo.moayoeats.domain.user.entity.*;
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
