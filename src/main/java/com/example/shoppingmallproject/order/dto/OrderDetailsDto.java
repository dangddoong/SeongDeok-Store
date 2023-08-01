package com.example.shoppingmallproject.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDetailsDto {
    private Long productId;
    private Long quantity;
}
