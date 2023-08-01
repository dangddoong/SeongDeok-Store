package com.example.shoppingmallproject.common.security.filter;

import com.example.shoppingmallproject.common.security.jwt.JwtUtil;
import com.example.shoppingmallproject.common.security.userDetails.service.UserDetailsServiceType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class UserAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String accessToken = jwtUtil.resolveToken(request, JwtUtil.AUTHORIZATION_HEADER);
      if (accessToken != null) {
        String loginId = jwtUtil.getLoginIdFromToken(accessToken);
        Authentication authentication = jwtUtil.createAuthentication(loginId,
            UserDetailsServiceType.USER);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
