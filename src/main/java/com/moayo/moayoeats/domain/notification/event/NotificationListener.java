package com.moayo.moayoeats.domain.notification.event;

import com.moayo.moayoeats.domain.notification.entity.*;
import com.moayo.moayoeats.domain.notification.repository.*;
import com.moayo.moayoeats.domain.user.entity.*;
import lombok.*;
import org.springframework.context.event.*;
import org.springframework.stereotype.*;

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
