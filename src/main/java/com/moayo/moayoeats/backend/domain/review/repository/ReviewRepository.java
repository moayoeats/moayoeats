package com.moayo.moayoeats.backend.domain.review.repository;

import com.moayo.moayoeats.backend.domain.review.entity.Review;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByUser(User user);
}
