package com.moayo.moayoeats.domain.offer.service;

import com.moayo.moayoeats.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.domain.offer.dto.response.OfferResponse;
import com.moayo.moayoeats.domain.user.entity.User;
import java.util.List;

public interface OfferService {

    /**
     * @param offerReq : 참가 신청시 필요한 요청 dto
     * @param user     : 해당 계정으로 로그인한 사용자
     */
    void applyParticipation(OfferRequest offerReq, User user);

    /**
     * @param offerReq : 참가 취소시 필요한 요청 dto
     * @param user     : 해당 계정으로 로그인한 사용자
     */
    void cancelParticipation(OfferRequest offerReq, User user);

    /**
     * @param offerReq : 참가신청 조회시 필요한 요청 dto
     * @param user     : 해당 계정으로 로그인한 사용자
     */
    List<OfferResponse> viewApplication(OfferRequest offerReq, User user);
}
