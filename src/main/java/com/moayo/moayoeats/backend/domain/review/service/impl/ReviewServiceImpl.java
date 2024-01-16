package com.moayo.moayoeats.backend.domain.review.service.impl;

import com.moayo.moayoeats.backend.domain.review.repository.ReviewRepository;
import com.moayo.moayoeats.backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

}
