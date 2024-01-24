package com.moayo.moayoeats.backend.domain.user.repository;

import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsById(Long userId);

    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

}
