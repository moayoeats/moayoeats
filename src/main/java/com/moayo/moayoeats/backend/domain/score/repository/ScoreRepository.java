package com.moayo.moayoeats.backend.domain.score.repository;

import com.moayo.moayoeats.backend.domain.score.entity.Score;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score,Long> {

    Optional<Score> findByUser(User user);

}
