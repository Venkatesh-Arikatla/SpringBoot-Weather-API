package com.example.lab4.dto.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class WeatherSummaryResponse {
    private String city;
    private String country;
    private Double temperature;
    private Double feelsLike;
    private String description;
    private String icon;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    public String getFormattedSummary() {
        return String.format("Weather in %s, %s: %s, Temperature: %.1f°C, Feels like: %.1f°C", 
                           city, country, description, temperature, feelsLike);
    }
}