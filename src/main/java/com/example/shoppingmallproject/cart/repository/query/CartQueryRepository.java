package com.example.shoppingmallproject.cart.repository.query;

import com.example.shoppingmallproject.cart.entity.Cart;

import java.util.List;

import java.util.Optional;

public interface CartQueryRepository {
    List<Cart> findCartsByUserId(Long userId);

    // QueryDSL 예시를 주기 위해 만든 매서드
    Optional<Cart> findCartWithProductsByUserId(Long userId);

    boolean isProductAlreadyExist(Long userId, Long productId);
}
