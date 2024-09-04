package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/prometheus", "/actuator/health/**",
                                "/swagger-ui", "/swagger-ui/**", "/error", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/storefront/carts", "/storefront/carts/**").hasRole("CUSTOMER")
                        .requestMatchers("/storefront/**").permitAll()
                        .requestMatchers("/backoffice/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        )
                .build();
    }
}