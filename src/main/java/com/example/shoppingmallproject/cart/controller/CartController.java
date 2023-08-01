package com.example.shoppingmallproject.cart.controller;

import com.example.shoppingmallproject.cart.dto.CartRequestDto;
import com.example.shoppingmallproject.cart.dto.CartsWithProductsDto;
import com.example.shoppingmallproject.cart.service.CartService;
import com.example.shoppingmallproject.common.security.userDetails.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * @param user 인증 객체
     * @return 장바구니 객체와 프로덕트 객체를 리턴합니다.
     */
    @GetMapping("/carts")
    public ResponseEntity<List<CartsWithProductsDto>> getCartsWithProducts(@AuthenticationPrincipal UserDetailsImpl user) {

        List<CartsWithProductsDto> cartsWithProducts = cartService.getCartsWithProducts(user.getUser().getId());
        if (cartsWithProducts.isEmpty()) return ResponseEntity.noContent().build();
        else return ResponseEntity.ok(cartsWithProducts);
    }

    @PostMapping("/carts")
    public ResponseEntity<String> createCart(@RequestBody CartRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails, UriComponentsBuilder uriBuilder) {
        try {
            Long cartId = cartService.createCart(dto, userDetails.getUser());
            URI location = uriBuilder.path("/carts/{id}").buildAndExpand(cartId).toUri();
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("상품이 이미 장바구니에 있습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("찾으시는 상품이 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            cartService.deleteCart(cartId, userDetails.getUser());
            return ResponseEntity.ok("장바구니 항목이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 접근입니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 항목이 존재하지 않습니다.");
        }
    }
}
