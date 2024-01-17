package com.moayo.moayoeats.backend.domain.notification.event;

import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void RequestNotify(Event event) {
        Notification newNotification = Notification.builder()
            .user(event.user())
            .notificationType(event.notificationType())
            .content(event.notificationType().getWord()).build();
        notificationRepository.save(newNotification);

    }

}
