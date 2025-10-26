package com.example.lab4.controller;

import com.example.lab4.dto.request.WeatherRequest;
import com.example.lab4.dto.request.CoordinatesRequest;
import com.example.lab4.dto.request.BulkWeatherRequest;
import com.example.lab4.dto.response.WeatherResponse;
import com.example.lab4.dto.response.WeatherSummaryResponse;
import com.example.lab4.dto.response.BulkWeatherResponse;
import com.example.lab4.service.WeatherService;
import com.example.lab4.util.WeatherValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class WeatherController {
    
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;
    
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<WeatherResponse> getWeatherByCity(@PathVariable String city) {
        logger.info("GET Weather request for city: {}", city);
        WeatherValidator.validateCity(city);
        
        WeatherResponse weatherData = weatherService.getWeatherByCity(city.trim());
        return ResponseEntity.ok(weatherData);
    }
    
    @GetMapping("/coordinates")
    public ResponseEntity<WeatherResponse> getWeatherByCoordinates(
            @RequestParam Double lat, @RequestParam Double lon) {
        logger.info("GET Weather request for coordinates: lat={}, lon={}", lat, lon);
        WeatherValidator.validateCoordinates(lat, lon);
        
        WeatherResponse weatherData = weatherService.getWeatherByCoordinates(lat, lon);
        return ResponseEntity.ok(weatherData);
    }
    
    @GetMapping("/city/{cityName}/country/{countryCode}")
    public ResponseEntity<WeatherResponse> getWeatherByCityAndCountry(
            @PathVariable String cityName, @PathVariable String countryCode) {
        logger.info("GET Weather request for city: {}, country: {}", cityName, countryCode);
        WeatherValidator.validateCity(cityName);
        WeatherValidator.validateCountryCode(countryCode);
        
        WeatherResponse weatherData = weatherService.getWeatherByCityAndCountry(cityName.trim(), countryCode);
        return ResponseEntity.ok(weatherData);
    }
    
    @GetMapping("/search")
    public ResponseEntity<WeatherResponse> getWeatherByCityParam(@RequestParam String city) {
        logger.info("GET Weather search request for city: {}", city);
        WeatherValidator.validateCity(city);
        
        WeatherResponse weatherData = weatherService.getWeatherByCity(city.trim());
        return ResponseEntity.ok(weatherData);
    }
    
    @GetMapping("/city/{city}/summary")
    public ResponseEntity<WeatherSummaryResponse> getWeatherSummary(@PathVariable String city) {
        logger.info("GET Weather summary request for city: {}", city);
        WeatherValidator.validateCity(city);
        
        WeatherSummaryResponse summary = weatherService.getWeatherSummary(city.trim());
        return ResponseEntity.ok(summary);
    }
    
    @PostMapping("/city")
    public ResponseEntity<WeatherResponse> getWeatherByCityPost(@Valid @RequestBody WeatherRequest request) {
        logger.info("POST Weather request for city: {}, country: {}, units: {}", 
                   request.getCity(), request.getCountryCode(), request.getUnits());
        
        WeatherResponse weatherData;
        if (request.getCountryCode() != null && !request.getCountryCode().isEmpty()) {
            weatherData = weatherService.getWeatherByCityAndCountry(
                request.getCity(), 
                request.getCountryCode(),
                request.getUnits()
            );
        } else {
            weatherData = weatherService.getWeatherByCity(request.getCity(), request.getUnits());
        }
        
        return ResponseEntity.ok(weatherData);
    }
    
    @PostMapping("/coordinates")
    public ResponseEntity<WeatherResponse> getWeatherByCoordinatesPost(@Valid @RequestBody CoordinatesRequest request) {
        logger.info("POST Weather coordinates request: lat={}, lon={}, units: {}", 
                   request.getLat(), request.getLon(), request.getUnits());
        
        WeatherResponse weatherData = weatherService.getWeatherByCoordinates(
            request.getLat(), 
            request.getLon(),
            request.getUnits()
        );
        
        return ResponseEntity.ok(weatherData);
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<BulkWeatherResponse> getBulkWeather(@Valid @RequestBody BulkWeatherRequest request) {
        logger.info("POST Bulk weather request for {} cities", request.getCities().size());
        
        long startTime = System.currentTimeMillis();
        BulkWeatherResponse bulkResponse = weatherService.getBulkWeather(request);
        long processingTime = System.currentTimeMillis() - startTime;
        
        bulkResponse.setProcessingTimeMs(processingTime);
        logger.info("Bulk weather processing completed in {} ms", processingTime);
        
        return ResponseEntity.ok(bulkResponse);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> weatherHealth() {
        try {
            weatherService.getWeatherByCity("London");
            
            return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Weather API",
                "timestamp", java.time.LocalDateTime.now(),
                "external_api", "OpenWeatherMap"
            ));
            
        } catch (Exception e) {
            logger.warn("Weather service health check failed: {}", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(
                        "status", "DOWN",
                        "service", "Weather API",
                        "error", e.getMessage(),
                        "timestamp", java.time.LocalDateTime.now()
                    ));
        }
    }
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getWeatherInfo() {
        return ResponseEntity.ok(Map.of(
            "service", "Weather API",
            "version", "1.0.0",
            "description", "Weather data integration with OpenWeatherMap API",
            "supported_features", List.of(
                "Current weather by city name",
                "Weather by coordinates (latitude/longitude)",
                "Weather by city and country code",
                "Bulk weather requests for multiple cities",
                "Weather summaries",
                "Multiple units support"
            )
        ));
    }
}