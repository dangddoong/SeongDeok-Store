package com.example.shoppingmallproject.cart.dto;

import com.example.shoppingmallproject.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CartRequestDto {
    private Long productId;
    private Long quantity;
}
