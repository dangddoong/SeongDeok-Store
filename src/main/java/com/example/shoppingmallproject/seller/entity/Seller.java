package com.example.shoppingmallproject.seller.entity;

import com.example.shoppingmallproject.product.entity.Product;
import com.example.shoppingmallproject.share.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends TimeStamped {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;
  private String phone;

  @OneToMany(fetch = FetchType.LAZY,mappedBy = "seller")
  private Set<Product> products = new LinkedHashSet<>();

  @Builder
  public Seller(String name, String email, String password, String phone) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }

  public void addProduct(Product product){
    this.products.add(product);
  }
}
