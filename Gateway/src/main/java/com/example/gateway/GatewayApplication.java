package com.example.gateway;

import com.example.gateway.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String httpUriFlashCard = uriConfiguration.getHttp("flashcard");
		return builder.routes()
				.route(p -> p
						.path("/box/study")
						.uri(httpUriFlashCard + "/box/study"))
				.route(p -> p
						.host("*.circuitbreaker.com")
						.filters(f -> f.circuitBreaker(config -> config
								.setName("mycmd")
								.setFallbackUri("forward:/fallback")))
						.uri(httpUriFlashCard))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}
}
