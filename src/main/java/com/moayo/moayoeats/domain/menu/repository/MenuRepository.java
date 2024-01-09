package com.moayo.moayoeats.domain.menu.repository;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByUserAndPost(User user, Post post);

}
