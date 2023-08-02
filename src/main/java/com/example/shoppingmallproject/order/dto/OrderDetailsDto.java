package com.example.shoppingmallproject.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDetailsDto {
    private Long productId;
    private Long quantity;
}
