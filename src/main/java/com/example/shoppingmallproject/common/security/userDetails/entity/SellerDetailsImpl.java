package com.example.shoppingmallproject.common.security.userDetails.entity;

import com.example.shoppingmallproject.seller.entity.Seller;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SellerDetailsImpl implements UserDetails {

  private final Seller seller;
  private final String username;
  private final Long sellerId;

  public SellerDetailsImpl(Seller seller, String email, Long sellerId) {
    this.seller = seller;
    this.username = email;
    this.sellerId = sellerId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
