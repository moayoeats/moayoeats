package com.moayo.moayoeats.domain.notification.entity;

import com.moayo.moayoeats.domain.user.entity.*;
import com.moayo.moayoeats.global.entity.*;
import jakarta.persistence.*;
import java.time.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_notification")
public class Notification extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private NotificationType fieldName;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Notification(NotificationType notificationType, String content, LocalDateTime createdAt,
        User user) {
        this.fieldName = notificationType;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}
