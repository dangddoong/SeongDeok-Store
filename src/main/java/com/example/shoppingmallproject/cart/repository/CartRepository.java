package com.example.shoppingmallproject.cart.repository;

import com.example.shoppingmallproject.cart.entity.Cart;
import com.example.shoppingmallproject.cart.repository.query.CartQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartQueryRepository {
}
