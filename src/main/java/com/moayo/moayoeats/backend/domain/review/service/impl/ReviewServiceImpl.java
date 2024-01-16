package com.moayo.moayoeats.backend.domain.review.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.order.dto.response.OrderResponse;
import com.moayo.moayoeats.backend.domain.order.entity.Order;
import com.moayo.moayoeats.backend.domain.order.exception.OrderErrorCode;
import com.moayo.moayoeats.backend.domain.order.repository.OrderRepository;
import com.moayo.moayoeats.backend.domain.review.dto.request.ReviewRequest;
import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import com.moayo.moayoeats.backend.domain.review.entity.Review;
import com.moayo.moayoeats.backend.domain.review.entity.ReviewEnum;
import com.moayo.moayoeats.backend.domain.review.repository.ReviewRepository;
import com.moayo.moayoeats.backend.domain.review.service.ReviewService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrders(User user) {

        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(
            order -> OrderResponse.builder().id(order.getId()).store(order.getStore()).menus(
                    order.getMenus().stream()
                        .map(menu -> new MenuResponse(menu.getMenuname(), menu.getPrice())).toList())
                .receiverName(order.getReceiver().getNickname()).build()).toList();

    }

    @Override
    public void review(ReviewRequest reviewReq, User user) {
        Order order = findOrderById(reviewReq.orderId());
        if (!order.getUser().getId().equals(user.getId())) {
            throw new GlobalException(OrderErrorCode.FORBIDDEN_ACCESS);
        }
        User receiver = order.getReceiver();
        checkIfUserExists(receiver.getId());
        Review review = findReviewByUser(receiver);
        updateReview(review, reviewReq);
        reviewRepository.save(review);
        orderRepository.delete(order);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new GlobalException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    private void checkIfUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new GlobalException(UserErrorCode.NOT_EXIST_USER);
        }
    }

    private Review findReviewByUser(User user) {
        //get Review when exists, make a new one when not
        return reviewRepository.findByUser(user).orElse(makeReview(user));
    }

    private Review makeReview(User user) {
        return new Review(user);
    }

    private void updateReview(Review review, ReviewRequest reviewReq) {
        review.increaseScoreAndCount(reviewReq.score());

        if (reviewReq.goodmanner()) {
            review.increaseGoodmanner();
        }
        if (reviewReq.goodcomm()) {
            review.increaseGoodcomm();
        }
        if (reviewReq.goodtime()) {
            review.increaseGoodtime();
        }
        if (reviewReq.badtime()) {
            review.increaseBadtime();
        }
        if (reviewReq.noshow()) {
            review.increaseNoshow();
        }
        if (reviewReq.nomoney()) {
            review.increaseNomoney();
        }
        if (reviewReq.badcomm()) {
            review.increaseBadcomm();
        }
        if (reviewReq.badmanner()) {
            review.increaseBadmanner();
        }
    }

    public ReviewResponse getReviews(User user) {
        Review review = findReviewByUser(user);
        Map<String, Integer> reviewMap = new HashMap<>();

        reviewMap.put(ReviewEnum.GOODMANNER.getComment(), review.getGoodmanner());
        reviewMap.put(ReviewEnum.GOODTIME.getComment(), review.getGoodtime());
        reviewMap.put(ReviewEnum.GOODCOMM.getComment(), review.getGoodcomm());
        reviewMap.put(ReviewEnum.BADTIME.getComment(), review.getBadtime());
        reviewMap.put(ReviewEnum.NOSHOW.getComment(), review.getNoshow());
        reviewMap.put(ReviewEnum.NOMONEY.getComment(), review.getNomoney());
        reviewMap.put(ReviewEnum.BADCOMM.getComment(), review.getBadcomm());
        reviewMap.put(ReviewEnum.BADMANNER.getComment(), review.getBadmanner());

        return ReviewResponse.builder()
            .reviews(reviewMap)
            .build();
    }

    public int getAvgScore(User user) {

        Review review = findReviewByUser(user);
        if (review.getCount() == 0) {
            return 0;
        }
        return review.getScore() / review.getCount();
    }

}
