package com.moayo.moayoeats.domain.offer.repository;

import com.moayo.moayoeats.domain.offer.entity.Offer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Offer> findByUserIdAndPostId(Long userId, Long postId);
}
