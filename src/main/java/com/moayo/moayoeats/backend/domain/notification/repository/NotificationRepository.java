package com.moayo.moayoeats.backend.domain.notification.repository;

import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long id);
}
