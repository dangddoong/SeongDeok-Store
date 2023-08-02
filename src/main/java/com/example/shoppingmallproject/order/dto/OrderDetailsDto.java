package com.example.shoppingmallproject.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class OrderDetailsDto {
    private Long productId;
    private Long quantity;
}
