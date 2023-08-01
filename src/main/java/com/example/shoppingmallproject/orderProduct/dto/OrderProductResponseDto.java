package com.example.shoppingmallproject.orderProduct.dto;

import com.example.shoppingmallproject.orderProduct.entity.OrderProduct;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductResponseDto {
    private Long id;
    private Long productId;
    private Long unitPrice;
    private Integer quantity;
    private String productName;

    public OrderProductResponseDto(OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.unitPrice = orderProduct.getUnitPrice();
        this.quantity = orderProduct.getQuantity();
        this.productName = orderProduct.getProduct().getName();
    }

    @QueryProjection
    public OrderProductResponseDto(Long id, Long productId, Long unitPrice, Integer quantity, String productName) {
        this.id = id;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.productName = productName;
    }
}
