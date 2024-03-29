package com.moayo.moayoeats.backend.domain.review.controller;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.review.dto.request.ReviewRequest;
import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import com.moayo.moayoeats.backend.domain.review.service.ReviewService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰를 작성할 Order들 조회하기
    @GetMapping("")
    public ApiResponse<List<OrderResponse>> getOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "모든 Order 조회에 성공했습니다.",
            reviewService.getOrders(userDetails.getUser()));
    }

    // 리뷰 작성
    @PostMapping("")
    public ApiResponse<Void> review(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewRequest reviewReq
    ) {
        reviewService.review(reviewReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "리뷰를 성공적으로 전송했습니다.");
    }

    @GetMapping("/all")
    public ApiResponse<ReviewResponse> getReviews(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "리뷰를 가져왔습니다.",
            reviewService.getReviews(userDetails.getUser())
        );
    }

    @GetMapping("/score")
    public ApiResponse<Double> getScore(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return new ApiResponse<>(
            HttpStatus.OK.value(),
            "평점을 가져왔습니다.",
            reviewService.getAvgScore(userDetails.getUser())
        );
    }

}
