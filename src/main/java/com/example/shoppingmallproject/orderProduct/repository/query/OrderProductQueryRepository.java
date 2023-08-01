package com.example.shoppingmallproject.orderProduct.repository.query;

import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;

import java.util.List;

public interface OrderProductQueryRepository {
    List<OrderProductResponseDto> getOrderProductDtos(Long orderId);
}
