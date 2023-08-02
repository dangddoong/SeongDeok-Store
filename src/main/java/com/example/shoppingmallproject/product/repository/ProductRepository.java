package com.example.shoppingmallproject.product.repository;

import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.query.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
    List<Product> findAllByIdIn(List<Long> productIds);

//    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET stock = stock - :quantity WHERE id = :productId AND stock >= :quantity", nativeQuery = true)
    int reduceStock(@Param("quantity") Long quantity, @Param("productId") Long productId);

}
