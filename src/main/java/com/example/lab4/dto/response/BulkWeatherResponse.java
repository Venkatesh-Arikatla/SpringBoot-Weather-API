package com.example.lab4.dto.response;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class BulkWeatherResponse {
    private List<String> requestedCities;
    private List<WeatherSummaryResponse> weatherData;
    private Map<String, String> failedCities;
    private Integer successCount;
    private Integer failureCount;
    private Long processingTimeMs;
    
    public BulkWeatherResponse() {
        this.successCount = 0;
        this.failureCount = 0;
    }
}