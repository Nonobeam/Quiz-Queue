package com.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthValidationFilter implements GatewayFilterFactory {
    private static final Logger log = LoggerFactory.getLogger(AuthValidationFilter.class);
    private final WebClient.Builder webClientBuilder;
    private final UriConfiguration uriConfiguration;

    @Override
    public GatewayFilter apply(Object config) {
        String httpUriAuth = uriConfiguration.getHttp("auth");

        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            return webClientBuilder.build()
                    .method(HttpMethod.POST)
                    .uri(httpUriAuth + "/vai")
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .retrieve()
                    .toEntity(String.class)
                    .flatMap(response -> chain.filter(exchange))
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("Unauthorized".getBytes());
                        log.error("Error: {}", authHeader);
                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    });
        };
    }
}

