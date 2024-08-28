package com.example.gateway.controller;

import com.example.gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ValidateRequestController {
    private static final Logger log = LogManager.getLogger(ValidateRequestController.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // For validate in coming request
    @PostMapping("/vai")
    public ResponseEntity<?> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token =  token.substring(7);
        String email = jwtService.extractMail(token);

        if (email != null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid(token, userDetails)) {
                log.info("Authentication successful for user {} {}", email, userDetails.getAuthorities());
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                log.error("Invalid token for user {}", email);
            }
        } else {
            log.error("User name is null.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User need to login again to continue using the website.");
    }
}