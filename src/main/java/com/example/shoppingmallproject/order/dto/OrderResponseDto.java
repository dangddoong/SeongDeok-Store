package com.example.shoppingmallproject.order.dto;

import com.example.shoppingmallproject.order.entity.Order;
import com.example.shoppingmallproject.order.entity.OrderStatusEnum;
import com.example.shoppingmallproject.orderProduct.dto.OrderProductResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Long totalPrice;
    private OrderStatusEnum orderStatus;
    private LocalDateTime orderDateTime;
    private List<OrderProductResponseDto> orderProductResponseDtos;

    private OrderResponseDto(Order order, List<OrderProductResponseDto> orderProductResponseDtos) {
        this.orderId = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.orderStatus = order.getOrderStatus();
        this.orderDateTime = order.getCreatedAt();
        this.orderProductResponseDtos = orderProductResponseDtos;
    }
    public static OrderResponseDto of(Order order, List<OrderProductResponseDto> orderProductResponseDtos){
        return new OrderResponseDto(order, orderProductResponseDtos);
    }
}
