package com.example.shoppingmallproject.seller.repository;

import com.example.shoppingmallproject.seller.entity.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
  Optional<Seller> findByEmail(String email);
}
