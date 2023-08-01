package com.example.shoppingmallproject.cart.dto;

import com.example.shoppingmallproject.cart.entity.Cart;
import com.example.shoppingmallproject.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class CartsWithProductsDto {
    private Cart cart;
    private Product product;

    private CartsWithProductsDto(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    public static CartsWithProductsDto of(Cart cart, Product product){
        return new CartsWithProductsDto(cart, product);
    }

    public List<CartsWithProductsDto> toDtoList(List<Cart> carts, List<Product> products){
//        List<CartsWithProductsDto> dtoList = new ArrayList<>();
//
//        for (int i = 0; i < carts.size(); i++) {
//            CartsWithProductsDto dto = CartsWithProductsDto.of(carts.get(i), products.get(i));
//            dtoList.add(dto);
//        }
//
//        return dtoList;

        return IntStream.range(0, carts.size()) // 새 dtoList 는 비어있으므로 IntStream.range() 매서드 통해 carts 의 길이만큼 stream
                .mapToObj(i -> CartsWithProductsDto.of(carts.get(i), products.get(i)))
                .toList();
    }
}
