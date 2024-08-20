package com.example.gateway;

import com.example.gateway.config.AuthValidationFilter;
import com.example.gateway.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.factory.SecureHeadersGatewayFilterFactory.Config;
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
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration, AuthValidationFilter authValidationFilter) {
		final String fallBack = "forward:/fallback";

		String httpUriFlashCard = uriConfiguration.getHttp("flashcard");
		String httpUriAuth = uriConfiguration.getHttp("auth");

		Config authConfig = new Config();
		return builder.routes()
				.route(p -> p
						.path("/auth/register")
						.filters(f -> f.circuitBreaker(config -> config
								.setName("register")
								.setFallbackUri(fallBack)))
						.uri(httpUriAuth + "/auth/register"))
				.route(p -> p
						.path("/auth/login")
						.filters(f -> f.circuitBreaker(config -> config
								.setName("login")
								.setFallbackUri(fallBack)))
						.uri(httpUriAuth + "/auth/login"))
				.route(p -> p
						.path("/box/study")
						.filters(f -> f
								.circuitBreaker(config -> config
										.setName("login")
										.setFallbackUri(fallBack))
								.filter(authValidationFilter.apply(authConfig)))
						.uri(httpUriFlashCard + "/box/study"))
				.build();
	}

	@RequestMapping("/fallback")
	public Mono<String> fallback() {
		return Mono.just("fallback");
	}
}
