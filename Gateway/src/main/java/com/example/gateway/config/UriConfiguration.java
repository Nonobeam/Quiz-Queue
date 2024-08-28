package com.example.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties
public class UriConfiguration {
    private final Map<String, String> httpBin = new HashMap<>();

    public UriConfiguration() {
        httpBin.put("test", "http://httpbin.org:80");
        httpBin.put("flashcard", "http://localhost:8081");
    }


    public String getHttp(String name) {
        return httpBin.get(name);
    }
}
