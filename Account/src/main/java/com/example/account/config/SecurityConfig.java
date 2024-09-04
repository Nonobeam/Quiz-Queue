package com.example.account.config;

import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
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
                        .authenticationProvider(provider)
                        .addFilterBefor()

                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                .authorizeHttpRequests().

                .addFilterBefore(jwtAuthenticationConverterForKeycloak())
                .addFilter()


                .build();
    }
}