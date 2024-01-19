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
import com.moayo.moayoeats.backend.domain.score.entity.Score;
import com.moayo.moayoeats.backend.domain.score.entity.ScoreEnum;
import com.moayo.moayoeats.backend.domain.score.repository.ScoreRepository;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.util.ArrayList;
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
    private final ScoreRepository scoreRepository;

    @Override
    public List<OrderResponse> getOrders(User user) {

        List<Order> orders = orderRepository.findAllByUser(user);
        return orders.stream().map(
            order -> OrderResponse.builder().id(order.getId()).store(order.getStore()).menus(
                    order.getMenus().stream()
                        .map(menu -> new MenuResponse(menu.getId(),menu.getMenuname(), menu.getPrice())).toList())
                .receiverName(order.getReceiver().getNickname()).build()).toList();

    }

    @Override
    public void review(ReviewRequest reviewReq, User user) {
        Order order = findOrderById(reviewReq.orderId());
        //check if the receiver exists, to clarify that the review hasn't been made, not because the user doesn't exist
        if (!order.getUser().getId().equals(user.getId())) {
            throw new GlobalException(OrderErrorCode.FORBIDDEN_ACCESS);
        }
        User receiver = order.getReceiver();
        checkIfUserExists(receiver.getId());
        updateScore(reviewReq.score(), receiver);
        updateReview(reviewReq, receiver);
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

    private void updateScore(ScoreEnum scoreEnum, User user) {
        Score score = findScoreByUser(user);
        score.update(scoreEnum);
        scoreRepository.save(score);
    }

    private Score findScoreByUser(User user) {
        return scoreRepository.findByUser(user).orElse(new Score(user));
    }

    private Review findReviewByContent(List<Review> reviews, ReviewEnum content, User user) {
        //get Review when exists, make a new one when not
        for (Review review : reviews) {
            if (review.getContent().equals(content)) {
                reviews.remove(review);
                return review;
            }
        }
        return new Review(user, content);
    }

    private void updateReview(ReviewRequest reviewReq, User receiver) {

        List<Review> reviews = reviewRepository.findAllByUser(receiver);
        List<Review> updated = new ArrayList<>();

        if (reviewReq.goodmanner()) {
            Review review = findReviewByContent(reviews, ReviewEnum.GOODMANNER, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.goodcomm()) {
            Review review = findReviewByContent(reviews, ReviewEnum.GOODCOMM, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.goodtime()) {
            Review review = findReviewByContent(reviews, ReviewEnum.GOODTIME, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.badtime()) {
            Review review = findReviewByContent(reviews, ReviewEnum.BADTIME, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.noshow()) {
            Review review = findReviewByContent(reviews, ReviewEnum.NOSHOW, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.nomoney()) {
            Review review = findReviewByContent(reviews, ReviewEnum.NOMONEY, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.badcomm()) {
            Review review = findReviewByContent(reviews, ReviewEnum.BADCOMM, receiver);
            review.increaseCount();
            updated.add(review);
        }
        if (reviewReq.badmanner()) {
            Review review = findReviewByContent(reviews, ReviewEnum.BADMANNER, receiver);
            review.increaseCount();
            updated.add(review);
        }
        reviewRepository.saveAll(updated);
    }

    public ReviewResponse getReviews(User user) {

        List<Review> reviews = reviewRepository.findAllByUser(user);
        Map<String, Integer> reviewMap = new HashMap<>();

        for (Review review : reviews) {
            reviewMap.put(review.getContent().getComment(), review.getCount());
        }

        return ReviewResponse.builder()
            .reviews(reviewMap)
            .build();
    }

    public Double getAvgScore(User user) {

        Score score = findScoreByUser(user);
        double total = score.getTotal();
        double count = score.getCount();
        if (count == 0) {
            return 0.0;
        }
        return total / count;
    }

}
