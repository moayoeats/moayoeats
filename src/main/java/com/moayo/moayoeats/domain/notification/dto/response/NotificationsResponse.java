package com.moayo.moayoeats.domain.notification.dto.response;

import com.moayo.moayoeats.domain.notification.entity.*;
import com.moayo.moayoeats.domain.user.entity.*;
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
