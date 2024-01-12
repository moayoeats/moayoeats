package com.moayo.moayoeats.backend.domain.notification.event;

import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.repository.NotificationRepository;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void RequestNotify(User user, Event event) {
        Notification newNotification = Notification.builder()
            .user(user)
            .notificationType(event.notificationType())
            .content(event.notificationType().getWord()).build();
        notificationRepository.save(newNotification);

    }

}
