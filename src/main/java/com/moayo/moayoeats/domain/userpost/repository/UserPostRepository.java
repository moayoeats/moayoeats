package com.moayo.moayoeats.domain.userpost.repository;

import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.domain.userpost.entity.UserPostId;
import com.moayo.moayoeats.domain.userpost.entity.UserPostRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UserPost, UserPostId> {

    List<UserPost> findAllByPostAndRoleEquals(Post post, UserPostRole role);

    List<UserPost> findAllByPost(Post post);

}
