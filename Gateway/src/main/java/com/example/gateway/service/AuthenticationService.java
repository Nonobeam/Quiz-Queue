package com.example.gateway.service;

import com.example.gateway.core.*;
import com.example.gateway.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    //----------------------------------- LOGIN -----------------------------------


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.error("Error while authenticating user", e);
        }

        String accountEmail = request.getEmail();
        Account account = accountRepository.findByEmail(accountEmail)
                .orElseThrow();

        var jwtToken = jwtService.generateToken(account);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(account.getRole())
                .build();
    }


    //----------------------------------- REGISTER -----------------------------------


    public String register(RegisterRequest request, Role role) {
        String accountEmail = request.getEmail();
        String accountPassword = request.getPassword();

        if (accountRepository.existsByEmail(accountEmail)) {
            log.error("Account with email {} already exists", accountEmail);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account with email " + accountEmail + " already exists");
        }

        Account account;
        try {
            account = Account.builder()
                    .email(accountEmail)
                    .password(passwordEncoder.encode(accountPassword))
                    .role(role)
                    .build();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Something went wrong while creating a new user, please check your input field");
        }

        accountRepository.save(account);

        return jwtService.generateToken(account);
    }
}
