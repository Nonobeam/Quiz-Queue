package com.example.gateway.controller;

import com.example.gateway.core.RegisterRequest;
import com.example.gateway.core.Role;
import com.example.gateway.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Role role = Role.STUDENT;
        String tokenResponse = authenticationService.register(request, role);
        return ResponseEntity.ok(tokenResponse);
    }
}
