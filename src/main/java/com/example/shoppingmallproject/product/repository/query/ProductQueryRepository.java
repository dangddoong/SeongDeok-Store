package com.example.shoppingmallproject.product.repository.query;

import com.example.shoppingmallproject.product.dto.ProductResponseDto;
import com.example.shoppingmallproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductQueryRepository {
    List<Product> getProductsByCarts(List<Long> cartIds);

    boolean existsBySellerAndName(String sellerName, String productName);

    Product getProductWithSeller(Long productId);

    List<ProductResponseDto> getMyProducts(Long sellerId);
}
