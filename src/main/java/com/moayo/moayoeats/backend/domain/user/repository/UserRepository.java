package com.moayo.moayoeats.backend.domain.user.repository;

import com.moayo.moayoeats.backend.domain.user.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.offers o WHERE o.id = :offerId")
    User findByOfferId(Long offerId);
}
