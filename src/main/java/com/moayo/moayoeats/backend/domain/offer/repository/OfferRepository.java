package com.moayo.moayoeats.backend.domain.offer.repository;

import com.moayo.moayoeats.backend.domain.offer.entity.Offer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Offer> findByUserIdAndId(Long userId, Long offerId);

    List<Offer> findAllByPostId(Long postId);

}
