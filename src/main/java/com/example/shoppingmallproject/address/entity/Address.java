package com.example.shoppingmallproject.address.entity;

import com.example.shoppingmallproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String zipCode;

    private boolean isDefaultAddress; // 기본 배송지 여부

    @Builder
    public Address(String userAddress, User user, String zipCode) {
        this.userAddress = userAddress;
        this.user = user;
        this.zipCode = zipCode;
        this.isDefaultAddress = false;
    }
}
