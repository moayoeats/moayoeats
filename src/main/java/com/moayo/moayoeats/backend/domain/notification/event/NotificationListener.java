package com.moayo.moayoeats.backend.domain.notification.event;

import com.moayo.moayoeats.backend.domain.notification.entity.*;
import com.moayo.moayoeats.backend.domain.notification.repository.*;
import lombok.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

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
