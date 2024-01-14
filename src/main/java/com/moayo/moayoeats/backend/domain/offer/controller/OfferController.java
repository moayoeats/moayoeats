package com.moayo.moayoeats.backend.domain.offer.controller;

import com.moayo.moayoeats.backend.domain.offer.dto.request.OfferRelatedPostRequest;
import com.moayo.moayoeats.backend.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.backend.domain.offer.dto.response.OfferResponse;
import com.moayo.moayoeats.backend.domain.offer.service.OfferService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import java.util.List;
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
        @RequestBody OfferRelatedPostRequest offerRelatedPostReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        offerService.applyParticipation(offerRelatedPostReq, userDetails.getUser());
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
    public ApiResponse<List<OfferResponse>> viewApplication(
        @RequestBody OfferRelatedPostRequest offerRelatedPostReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        List<OfferResponse> offerResList = offerService.viewApplication(
            offerRelatedPostReq,
            userDetails.getUser()
        );

        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "해당 게시글 참가신청 목록을 가져왔습니다.",
            offerResList
        );
    }

    @PostMapping("/approve")
    public ApiResponse<Void> approveApplication(
        @RequestBody OfferRequest offerReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        offerService.approveApplication(offerReq, userDetails.getUser());

        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "참가 신청을 승인하였습니다."
        );
    }

    @PostMapping("/reject")
    public ApiResponse<Void> rejectApplication(
        @RequestBody OfferRequest offerReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        offerService.rejectApplication(offerReq, userDetails.getUser());

        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "참가 신청을 거부하였습니다."
        );
    }

    @DeleteMapping("/after")
    public void cancelAfterApproval(
        @RequestBody OfferRelatedPostRequest offerRelatedPostReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

    }

}
