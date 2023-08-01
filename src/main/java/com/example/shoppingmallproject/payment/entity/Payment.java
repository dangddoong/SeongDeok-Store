package com.example.shoppingmallproject.payment.entity;

import com.example.shoppingmallproject.share.TimeStamped;
import com.example.shoppingmallproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private String payMethod;

    @Column(nullable = false)
    private Long payNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User users;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ORDER_ID")
//    private Orders order;

    private void setPayMethod(String payMethod){
        this.payMethod = payMethod;
    }

    @Builder
    public Payment(Long totalPrice, String payMethod, Long payNumber, User users) {
        this.totalPrice = totalPrice;
        this.payMethod = payMethod;
        this.payNumber = payNumber;
        this.users = users;
    }
}
