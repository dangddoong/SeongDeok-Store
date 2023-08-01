package com.example.shoppingmallproject.common.security.userDetails.entity;

import com.example.shoppingmallproject.user.entity.User;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

  private final User user;
  private final String username;
  private final Long userId;

  public UserDetailsImpl(User user, String email, Long userId) {
    this.user = user;
    this.username = email;
    this.userId = userId;
  }

  public User getUser() { return user; }
  public Long getUserId() { return userId; }
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
