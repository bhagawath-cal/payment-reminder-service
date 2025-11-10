// src/main/java/com/paymentreminder/service/config/SecurityConfig.java
package com.paymentreminder.service.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Actuator matchers
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.HealthEndpointWebExtension;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1) // make sure this wins
public class SecurityConfig {

  @Bean
  SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // health is GET-only, but be explicit
      .authorizeHttpRequests(auth -> auth
        // Permit Actuator health (both servlet + web extension)
        .requestMatchers(EndpointRequest.to(HealthEndpoint.class, HealthEndpointWebExtension.class)).permitAll()
        // Your custom health endpoints
        .requestMatchers("/healthz", "/api/health", "/api/health/**").permitAll()
        // Everything else must auth
        .anyRequest().authenticated()
      )
      .httpBasic(basic -> {}); // 401 on protected paths
    return http.build();
  }
}
