package com.example.shoppingmallproject.common.security.userDetails.entity;

import com.example.shoppingmallproject.admin.entity.Admin;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminDetailsImpl implements UserDetails {

  private final Admin admin;
  private final String username;
  private final Long adminId;

  public AdminDetailsImpl(Admin admin, String email, Long adminId) {
    this.admin = admin;
    this.username = email;
    this.adminId = adminId;
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
