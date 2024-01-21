package com.moayo.moayoeats.backend.domain.offer.repository;

import com.moayo.moayoeats.backend.domain.offer.entity.Offer;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Offer> findByUserIdAndId(Long userId, Long offerId);

    List<Offer> findAllByPostId(Long postId);

    @Query("SELECT u FROM User u JOIN u.offers o WHERE o.id = :offerId")
    User findByOfferId(Long offerId);

    Offer findByUserIdAndPostId(Long userId, Long postId);
}
