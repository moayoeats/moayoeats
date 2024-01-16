package com.moayo.moayoeats.backend.domain.order.repository;

import com.moayo.moayoeats.backend.domain.order.entity.Order;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUser(User user);

}
