package com.moayo.moayoeats.backend.domain.pushEvent;

import com.moayo.moayoeats.backend.domain.pushEvent.exception.PushEventErrorCode;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class PushEventService {

    public SseEmitter subscribe(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
            System.out.println("SSE 연결 성공");
        } catch (IOException e) {
            throw new GlobalException(PushEventErrorCode.INTERNAL_SERVER_ERROR);
        }

        PushEventController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> PushEventController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> PushEventController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> PushEventController.sseEmitters.remove(userId));

        return sseEmitter;
    }

    //방장에게 push알림
    public void notifyApplyParticipation(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("apply")
                    .data("참가 요청 신청이 도착했습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //유저에게 push알림
    public void notifyApprove(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("approved")
                    .data("참가요청이 승인되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //유저에게 push알림

    public void notifyReject(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("rejected")
                    .data("참가요청이 거절되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //방장에게 push알림
    public void notifyAmoutCollected(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("collected")
                    .data("목표 금액이 충당 되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //방장에게 push알림
    public void notifyAmoutIsNotCollected(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("not-collected")
                    .data("목표 금액에 미달 하였습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //유저에게 push알림
    public void notifyCloseApplication(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("closed")
                    .data("모집이 완료되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //모두에게 push알림
    public void postDeleted(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("completed")
                    .data("모집글이 삭제 되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

}
