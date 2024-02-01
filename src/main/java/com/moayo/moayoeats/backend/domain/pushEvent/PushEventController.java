package com.moayo.moayoeats.backend.domain.pushEvent;

import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification-push")
public class PushEventController {

    private final PushEventService notificationService;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping("/subscribe")
    public SseEmitter notification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        SseEmitter sseEmitter = notificationService.subscribe(userDetails.getUser().getId());

        return sseEmitter;
    }
}
