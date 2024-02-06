package com.moayo.moayoeats.backend.domain.notification.service.impl;

import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.repository.NotificationRepository;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("알림기능의 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @DisplayName("알림 목록 조회")
    @Test
    void get_notifications_success() {
        //Given
        String email3 = "host@moayo.eats";
        String password3 = "moayoEATS@4";
        String nickname3 = "host";
        User user_host = User.builder()
            .email(email3)
            .password(password3)
            .nickname(nickname3)
            .build();

        Notification notification1 = Notification.builder()
            .user(user_host)
            .notificationType(NotificationType.PARTICIPANT_JOIN_REQUEST)
            .content(NotificationType.PARTICIPANT_JOIN_REQUEST.getWord())
            .build();

        Notification notification2 = Notification.builder()
            .user(user_host)
            .notificationType(NotificationType.PARTICIPANT_JOIN_REQUEST)
            .content(NotificationType.PARTICIPANT_JOIN_REQUEST.getWord())
            .build();

        //given 안에 정확한 값을 넣어버리면 안됨
        //하위 메서드에 대해서는 검증을 하지 않는다.
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user_host));
        given(notificationRepository.findAllByUserIdOrderByCreatedAtDesc(
            any())).willReturn(
            List.of(notification1, notification2));

        //When
        List<NotificationsResponse> result = notificationService.getNotifications(user_host);

        //하위 메서드가 호출이 되었는지 확인을 해야한다.
        //서비스에서 리턴하는 객체가 의도한 값을 리턴하는지 확인하는 것
        //Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).notificationType()).isEqualTo(
            NotificationType.PARTICIPANT_JOIN_REQUEST);
        assertThat(result.get(1).notificationType()).isEqualTo(
            NotificationType.PARTICIPANT_JOIN_REQUEST);
    }

}