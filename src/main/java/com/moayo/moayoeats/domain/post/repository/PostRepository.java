package com.moayo.moayoeats.domain.post.repository;

import com.moayo.moayoeats.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);

    Optional<List<Post>> findAllByCategoryEquals(String category);

}
