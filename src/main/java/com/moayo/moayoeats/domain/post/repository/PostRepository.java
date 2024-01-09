package com.moayo.moayoeats.domain.post.repository;

import com.moayo.moayoeats.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
