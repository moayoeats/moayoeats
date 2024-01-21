package com.moayo.moayoeats.backend.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    //방장에게 Host
    PARTICIPANT_JOIN_REQUEST("참가 요청 신청이 도착했습니다!"),
    AMOUNT_COLLECTED("설정한 금액이 모두 모였습니다!"),
    AMOUNT_IS_NOT_COLLECTED("설정한 금액에 미달하였습니다!"),

    //참가자에게 Participant
    PARTICIPANT_REJECTED("참가 요청이 거절 되었습니다!"),
    PARTICIPANT_APPROVED("참가 요청이 승인 되었습니다!"),
    MEETING_ACTIVATED("모임이 활성화 되었습니다!"),
    MEETING_DELETED("모집글이 삭제 되었습니다!");

    private final String word;

}