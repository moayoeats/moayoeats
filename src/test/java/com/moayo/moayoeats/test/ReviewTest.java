package com.moayo.moayoeats.test;

import com.moayo.moayoeats.backend.domain.review.dto.response.ReviewResponse;
import java.util.HashMap;
import java.util.Map;

public interface ReviewTest {

    Double TEST_USER_SCORE = 4.5;
    Map<String, Integer> TEST_REVIEWS = new HashMap<>() {
        {
            put("review1", 5); // comment, comment count
            put("review2", 2);
        }
    };

    ReviewResponse TEST_REVIEW_RES =
        ReviewResponse.builder()
            .reviews(TEST_REVIEWS)
            .build();
}