package com.example.shoppingmallproject.delivery.entity;

import com.example.shoppingmallproject.share.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String address;

    @Builder
    public Delivery(String address) {
        this.address = address;
    }
}
