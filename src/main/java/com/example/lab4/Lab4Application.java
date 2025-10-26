package com.example.lab4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;

@SpringBootApplication
public class Lab4Application {

    private static final Logger logger = LoggerFactory.getLogger(Lab4Application.class);

    public static void main(String[] args) {
        try {
            logger.info("🚀 Starting Lab 4 - Weather API Application...");
            SpringApplication.run(Lab4Application.class, args);
            logger.info("✅ Lab 4 Application started successfully!");
            logger.info("🌤️  Weather API: Integrated with OpenWeatherMap");
            logger.info("🌐 Server running on: http://localhost:8080");
            
        } catch (Exception e) {
            logger.error("❌ Failed to start Lab 4 Application", e);
            throw e;
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(30))
            .build();
    }
}