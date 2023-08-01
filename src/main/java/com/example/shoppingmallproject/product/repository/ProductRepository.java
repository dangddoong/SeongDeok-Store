package com.example.shoppingmallproject.product.repository;

import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.product.repository.query.ProductQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
    List<Product> findAllByIdIn(List<Long> productIds);
}
