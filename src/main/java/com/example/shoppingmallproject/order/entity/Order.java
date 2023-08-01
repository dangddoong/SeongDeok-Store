package com.example.shoppingmallproject.order.entity;

import com.example.shoppingmallproject.share.TimeStamped;
import com.example.shoppingmallproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(nullable = false)
    private Long totalPrice;
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatus;
    // 추후에 배송과 관련된 Enum이 필요하다면, 따로 만들어서 넣어줍시다. + '배송 주소지 필드'도 같이 넣어줘야 합니다.
    @Builder
    public Order(User user, Long totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatusEnum.PROGRESSING;
    }
}
