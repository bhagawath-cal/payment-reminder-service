// src/main/java/com/paymentreminder/service/config/SecurityConfig.java
package com.paymentreminder.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        // ✅ Permit health endpoints (Actuator + your custom one)
        .requestMatchers("/actuator/health", "/actuator/health/**", "/healthz").permitAll()
        // (Optional) permit info too, if you expose it
        .requestMatchers("/actuator/info").permitAll()
        // everything else must authenticate
        .anyRequest().authenticated()
      )
      // keep basic on, so non-health paths give 401 (not 403) without creds
      .httpBasic(basic -> {});
    return http.build();
  }
}
