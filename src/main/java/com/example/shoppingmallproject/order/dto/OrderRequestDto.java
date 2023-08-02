package com.example.shoppingmallproject.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequestDto {
    private List<OrderDetailsDto> orderDetailsDtos;

    public List<Long> getProductIds(){
        List<Long> productIds = new ArrayList<>();
        orderDetailsDtos.forEach(dto -> productIds.add(dto.getProductId()));
        return productIds;
    }
}
