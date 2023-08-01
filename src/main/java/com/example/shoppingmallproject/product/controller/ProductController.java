package com.example.shoppingmallproject.product.controller;

import com.example.shoppingmallproject.common.security.userDetails.entity.SellerDetailsImpl;
import com.example.shoppingmallproject.product.dto.ProductRequestDto;
import com.example.shoppingmallproject.product.dto.ProductResponseDto;
import com.example.shoppingmallproject.product.service.ProductService;
import com.example.shoppingmallproject.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@AuthenticationPrincipal Seller seller, @RequestBody ProductRequestDto dto) {
        Long productId = productService.createProduct(seller, dto);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@AuthenticationPrincipal Seller seller, @PathVariable Long productId, @RequestBody ProductRequestDto dto) {
        productService.updateProduct(seller, productId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@AuthenticationPrincipal Seller seller, @PathVariable Long productId) {
        productService.deleteProduct(seller, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/seller")
    public ResponseEntity<List<ProductResponseDto>> getSellersProducts(@AuthenticationPrincipal Seller seller){
        List<ProductResponseDto> products = productService.getSellersProducts(seller);
        if  (products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }
}
