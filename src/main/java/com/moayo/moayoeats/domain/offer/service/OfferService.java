package com.moayo.moayoeats.domain.offer.service;

import com.moayo.moayoeats.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.domain.user.entity.User;

public interface OfferService {

    /**
     *
     * @param offerReq : 참가 신청/취소시 필요한 요청 dto
     * @param user : 해당 계정으로 로그인한 사용자
     */
    void applyToParticipate(OfferRequest offerReq, User user);
}
