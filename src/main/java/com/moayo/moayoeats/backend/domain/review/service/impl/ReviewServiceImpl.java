package com.moayo.moayoeats.backend.domain.review.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.order.entity.Order;
import com.moayo.moayoeats.backend.domain.order.repository.OrderRepository;
import com.moayo.moayoeats.backend.domain.review.repository.ReviewRepository;
import com.moayo.moayoeats.backend.domain.review.service.ReviewService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getOrders(User user) {

        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(
            order -> OrderResponse.builder().id(order.getId()).store(order.getStore()).menus(
                    order.getMenus().stream()
                        .map(menu -> new MenuResponse(menu.getMenuname(), menu.getPrice())).toList())
                .receiverName(order.getReceiver().getNickname()).build()).toList();

    }
}
