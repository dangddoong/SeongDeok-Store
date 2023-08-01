package com.example.shoppingmallproject.orderProduct.entity;

import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.seller.entity.Seller;
import com.example.shoppingmallproject.share.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Long unitPrice;

    //    기능 고도화 단계에서 OrderProduct에 Enum 추가하겠습니다. (결제, 배송 Enum)
    //    private OrderStatusEnum orderStatus;
    @Builder
    public OrderProduct(Order order, Product product, Integer quantity, Long unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
