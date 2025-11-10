package com.paymentreminder.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  // Chain 1: match ONLY health endpoints → permitAll
  @Bean
  @Order(1)
  SecurityFilterChain healthChain(HttpSecurity http) throws Exception {
    http
      // In your Spring Security version this overload takes String patterns
      .securityMatcher(
        "/healthz",
        "/actuator/health", "/actuator/health/**",
        "/api/health", "/api/health/**"
      )
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    return http.build();
  }

  // Chain 2: everything else → authenticated
  @Bean
  @Order(2)
  SecurityFilterChain appChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
      .httpBasic(basic -> {});
    return http.build();
  }
}
