package com.example.lab4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Map<String, Object> getApiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lab 4 - Weather API Integration");
        response.put("version", "1.0.0");
        response.put("status", "Running");
        response.put("description", "Spring Boot REST API with OpenWeatherMap Integration");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("weather_by_city", "GET /api/weather/city/{cityName}");
        endpoints.put("weather_by_coordinates", "GET /api/weather/coordinates?lat=X&lon=Y");
        endpoints.put("weather_by_city_country", "GET /api/weather/city/{city}/country/{code}");
        endpoints.put("weather_search", "GET /api/weather/search?city=NAME");
        endpoints.put("weather_summary", "GET /api/weather/city/{city}/summary");
        endpoints.put("bulk_weather", "POST /api/weather/bulk");
        endpoints.put("health", "GET /api/weather/health");
        endpoints.put("info", "GET /api/weather/info");
        
        response.put("endpoints", endpoints);
        return response;
    }
    
    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Lab 4 Weather API");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return response;
    }
}