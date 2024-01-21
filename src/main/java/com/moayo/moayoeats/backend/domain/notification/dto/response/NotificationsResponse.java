package com.moayo.moayoeats.backend.domain.notification.dto.response;

import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NotificationsResponse(
    NotificationType type,
    String fieldContent,
    LocalDateTime createdAt
) {

    public static NotificationsResponse formWith(Notification notification) {
        return NotificationsResponse.builder()
            .type(notification.getFieldName())
            .fieldContent(notification.getContent())
            .createdAt(notification.getCreatedAt())
            .build();
    }
}
