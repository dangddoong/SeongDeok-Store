package com.example.shoppingmallproject.cart.entity;

import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long quantity;

    @Builder
    public Cart(Product product, User user, Long quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }
}
