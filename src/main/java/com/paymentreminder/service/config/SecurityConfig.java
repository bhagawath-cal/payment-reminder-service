package com.paymentreminder.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/", "/index.html", "/healthz",
                           "/actuator/health", "/actuator/health/**",
                           "/api/**").permitAll()
          .anyRequest().permitAll()
      )
      .httpBasic(basic -> basic.disable())
      .formLogin(form -> form.disable())
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
