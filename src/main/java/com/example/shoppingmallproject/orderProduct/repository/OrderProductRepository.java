package com.example.shoppingmallproject.orderProduct.repository;

import com.example.shoppingmallproject.orderProduct.entity.OrderProduct;
import com.example.shoppingmallproject.orderProduct.repository.query.OrderProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductQueryRepository {
}
