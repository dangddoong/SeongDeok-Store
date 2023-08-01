package com.example.shoppingmallproject.order.repository;

import com.example.shoppingmallproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
