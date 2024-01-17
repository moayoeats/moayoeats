package com.moayo.moayoeats.backend.domain.review.service;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.review.dto.request.ReviewRequest;
import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface ReviewService {

    /**
     * @param user : Order 조회자
     * @return : OrderResponse
     */
    public List<OrderResponse> getOrders(User user);

    /**
     * @param reviewReq : 리뷰 작성 dto
     * @param user      : 리뷰 작성자
     */
    public void review(ReviewRequest reviewReq, User user);

    /**
     * @param user : 조회되는 마이페이지의 주인
     * @return : ReviewResponse
     */
    ReviewResponse getReviews(User user);
}
