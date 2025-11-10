package com.paymentreminder.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  // Chain 1: match ONLY health endpoints → permitAll
  @Bean
  @Order(1)
  SecurityFilterChain healthChain(HttpSecurity http) throws Exception {
    http
      .securityMatcher(
        new AntPathRequestMatcher("/healthz"),
        new AntPathRequestMatcher("/actuator/health"),
        new AntPathRequestMatcher("/actuator/health/**"),
        new AntPathRequestMatcher("/api/health"),
        new AntPathRequestMatcher("/api/health/**")
      )
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    return http.build();
  }

  // Chain 2: everything else → authenticated (basic)
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
