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
                    .name("message")
                    .data("참가요청이 들어왔습니다!"));
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
                    .name("message")
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
                    .name("message")
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
                    .name("message")
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
                    .name("message")
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
                    .name("message")
                    .data("모집이 완료되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //모두에게 push알림
    public void notifyCancelPost(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("message")
                    .data("모집이 취소되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }

    //모두에게 push알림
    public void notifyDeletePost(Long userId) {

        if (PushEventController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = PushEventController.sseEmitters.get(userId);

            try {
                sseEmitterReceiver.send(SseEmitter
                    .event()
                    .name("message")
                    .data("모집인원 모두 수령 완료 후 글이 삭제되었습니다!"));
            } catch (IOException e) {
                PushEventController.sseEmitters.remove(userId);
            }
        }
    }
}
