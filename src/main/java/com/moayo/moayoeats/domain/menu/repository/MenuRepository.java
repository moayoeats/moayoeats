package com.moayo.moayoeats.domain.menu.repository;

import com.moayo.moayoeats.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
