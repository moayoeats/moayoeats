package com.moayo.moayoeats.domain.offer.controller;

import com.moayo.moayoeats.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.domain.offer.service.OfferService;
import com.moayo.moayoeats.global.dto.ApiResponse;
import com.moayo.moayoeats.global.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private final OfferService offerService;

    @PostMapping()
    public ApiResponse<Void> applyParticipation(
        @RequestBody OfferRequest offerReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        offerService.applyParticipation(offerReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "참가신청이 완료되었습니다.");
    }

    @DeleteMapping()
    public ApiResponse<Void> cancelParticipation(
        @RequestBody OfferRequest offerReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        offerService.cancelParticipation(offerReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "참가취소가 완료되었습니다.");
    }

    @GetMapping()
    public void viewApplication(
        @RequestBody OfferRequest offerReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

    }

}
