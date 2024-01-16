package com.moayo.moayoeats.backend.domain.review.repository;

import com.moayo.moayoeats.backend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
