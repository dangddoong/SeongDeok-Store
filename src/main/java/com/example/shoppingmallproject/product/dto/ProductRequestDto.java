package com.example.shoppingmallproject.product.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private String detail;
    private Long price;
    private Long stock;
}
