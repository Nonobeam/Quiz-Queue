package com.example.authentication.controller;

import com.example.authentication.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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
        String email = jwtService.extractMail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid(token, userDetails)) {
                log.info("Authentication successful for user {} {} {}", email, " " ,userDetails.getAuthorities());
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
            } else {
                log.error("Invalid token for user {}", email);
            }
        } else {
            log.error("User name is null or authentication is already set");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User need to login again to continue using the website.");
    }
}