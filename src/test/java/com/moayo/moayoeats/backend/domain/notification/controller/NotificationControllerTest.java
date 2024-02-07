package com.moayo.moayoeats.backend.domain.notification.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.moayo.moayoeats.backend.domain.BaseMvcTest;
import com.moayo.moayoeats.backend.domain.notification.dto.response.NotificationsResponse;
import com.moayo.moayoeats.backend.domain.notification.entity.Notification;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.repository.NotificationRepository;
import com.moayo.moayoeats.backend.domain.notification.service.NotificationService;
import com.moayo.moayoeats.backend.domain.notification.service.impl.NotificationServiceImpl;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import com.moayo.moayoeats.global.MockSpringSecurityFilter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.IMAGE_JPEG;

@DisplayName("알림기능의 컨트롤러 테스트")
@WebMvcTest(controllers = NotificationController.class)
class NotificationControllerTest extends BaseMvcTest {

    @MockBean
    private NotificationServiceImpl notificationService;

    @DisplayName("알림목록 조회 요청")
    @Test
    void getNotifications() throws Exception {
        //Given
        //데이터는 통신할 때 필요한 것 들만 사용하면 된다.
        //Controller 테스트시 필요한 것 Req 서비스에서 반환해주는 Res
        //Entity를 몰라야한다
        List<NotificationsResponse> testNotificationsRes = new ArrayList<>();
        NotificationsResponse notificationsResponse = NotificationsResponse.builder()
            .notificationType(NotificationType.PARTICIPANT_JOIN_REQUEST)
            .fieldContent("참가 요청 신청이 도착했습니다!")
            .createdAt(LocalDateTime.now())
            .build();

        //When
        when(notificationService.getNotifications(any())).thenReturn(testNotificationsRes);

        //Then
        mockMvc.perform(get("/api/v1/notifications")
                .principal(mockPrincipal))
            .andExpect(status().isOk());
    }

}