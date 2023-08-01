package com.example.shoppingmallproject.delivery.repository.query;

import com.example.shoppingmallproject.delivery.entity.Delivery;

import java.util.List;

public interface DeliveryQueryRepository {
    List<Delivery> findDeliveryByUserId(Long userId);
}
