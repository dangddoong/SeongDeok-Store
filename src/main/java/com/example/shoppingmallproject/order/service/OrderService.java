package com.example.shoppingmallproject.order.service;

import com.example.shoppingmallproject.order.dto.OrderRequestDto;
import com.example.shoppingmallproject.order.dto.OrderResponseDto;
import com.example.shoppingmallproject.user.entity.User;

public interface OrderService {
    Long createOrder(OrderRequestDto dto, User user);

    OrderResponseDto getOrder(Long orderId);

}
