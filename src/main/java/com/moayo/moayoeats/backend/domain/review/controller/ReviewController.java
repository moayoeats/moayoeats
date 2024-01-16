package com.moayo.moayoeats.backend.domain.review.controller;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.backend.domain.review.service.ReviewService;
import com.moayo.moayoeats.backend.global.dto.ApiResponse;
import com.moayo.moayoeats.backend.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰를 작성할 Order들 조회하기
    @GetMapping("/reviews")
    public ApiResponse<List<OrderResponse>> getOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return new ApiResponse<>(HttpStatus.OK.value(), "모든 Order 조회에 성공했습니다.",
            reviewService.getOrders(userDetails.getUser()));
    }

}
