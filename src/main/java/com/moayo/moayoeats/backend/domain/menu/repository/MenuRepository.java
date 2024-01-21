package com.moayo.moayoeats.backend.domain.menu.repository;

import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByUserAndPost(User user, Post post);

    List<Menu> findAllByPost(Post post);

    List<Menu> findAllByPostId(Long postId);
}
