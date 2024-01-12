package com.moayo.moayoeats.backend.domain.notification.dto.response;

import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.notification.entity.*;
import com.moayo.moayoeats.backend.domain.user.entity.*;
import java.time.*;
import lombok.*;

@Builder
public record NotificationsResponse(
    User user,
    NotificationType type,
    String fieldContent,
    LocalDateTime createdAt
) {

    public static NotificationsResponse formWith(Notification notification) {
        return NotificationsResponse.builder()
            .user(notification.getUser())
            .type(notification.getFieldName())
            .fieldContent(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
