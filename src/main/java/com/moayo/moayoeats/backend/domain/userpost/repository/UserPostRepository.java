package com.moayo.moayoeats.backend.domain.userpost.repository;

import com.moayo.moayoeats.backend.domain.post.entity.*;
import com.moayo.moayoeats.backend.domain.user.entity.*;
import com.moayo.moayoeats.backend.domain.userpost.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface UserPostRepository extends JpaRepository<UserPost, UserPostId> {

    List<UserPost> findAllByPostAndRoleEquals(Post post, UserPostRole role);

    List<UserPost> findAllByPost(Post post);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<UserPost> findByUserIdAndPostId(Long userId, Long postId);

    boolean existsByUserIdAndPostIdAndRole(Long userId, Long postId, UserPostRole role);

    Optional<UserPost> findByPostAndUserAndRoleEquals(Post post, User user, UserPostRole role);

    @Query("SELECT u FROM User u JOIN UserPost up ON u.id = up.user.id WHERE up.role = :role AND up.post.id = :postId")
    User findByPostIdAndRole(Long postId, UserPostRole role);
}
