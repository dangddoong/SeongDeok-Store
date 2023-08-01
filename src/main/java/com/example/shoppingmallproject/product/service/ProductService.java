package com.example.shoppingmallproject.product.service;

import com.example.shoppingmallproject.product.dto.ProductRequestDto;
import com.example.shoppingmallproject.product.dto.ProductResponseDto;
import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.seller.entity.Seller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {

    Product getProductById(Long productId);

    List<Product> getProductsByIds(List<Long> productIds);

    List<Product> getUsersProductsByCartIds(List<Long> cartIds);

    Long createProduct(Seller seller, ProductRequestDto dto);

    void updateProduct(Seller seller, Long productId, ProductRequestDto dto);

    void deleteProduct(Seller seller, Long productId);

    List<ProductResponseDto> getSellersProducts(Seller seller);
}
