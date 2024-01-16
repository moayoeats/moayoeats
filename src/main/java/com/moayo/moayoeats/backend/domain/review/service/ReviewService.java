package com.moayo.moayoeats.backend.domain.review.service;

import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;

public interface ReviewService {

    /**
     *
     * @param user : Order 조회자
     * @return : OrderResponse
     */
    public List<OrderResponse> getOrders(User user);

}
