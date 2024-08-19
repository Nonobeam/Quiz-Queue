package com.example.authentication.auth;

import com.example.authentication.service.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// OncePerRequestFilter: run each times request arrive
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LogManager.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the Authorization header from the HTTP request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        // Check if the Authorization header is missing or doesn't start with "Bearer"
        // If so, pass the request and response to the next filter in the chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwt = authHeader.substring(7); // Remove "Bearer " from the beginning

        // Extract the username from the JWT token using a service
        email = jwtService.extractMail(jwt);

        // If the username is not null and there is no authentication in the current SecurityContext
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Get UserDetails from the database using the username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            // Check if the JWT token is valid for the UserDetails
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create an Authentication token for the user
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No password is needed for JWT authentication
                        userDetails.getAuthorities()
                );
                // Set additional details for the authentication token
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Set the authentication token in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Authentication successful for user {} {} {}", email, " " ,userDetails.getAuthorities());
            } else {
                log.error("Invalid token for user {}", email);
            }
        } else {
            log.error("User name is null or authentication is already set");
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
