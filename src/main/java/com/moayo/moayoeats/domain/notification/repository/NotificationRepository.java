package com.moayo.moayoeats.domain.notification.repository;

import com.moayo.moayoeats.domain.notification.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserId(Long id);
}
