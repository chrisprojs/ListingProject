package com.exercise.listingproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    // Add RestTemplate bean for making HTTP requests to other services or external APIs.
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
