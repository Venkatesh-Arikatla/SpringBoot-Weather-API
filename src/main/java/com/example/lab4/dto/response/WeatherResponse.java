package com.example.lab4.dto.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class WeatherResponse {
    private String city;
    private String country;
    private Double temperature;
    private Double feelsLike;
    private String description;
    private String weatherCondition;
    private Integer humidity;
    private Integer pressure;
    private Double windSpeed;
    private Integer visibility;
    private String icon;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private Coordinates coordinates;
    private SunTimes sunTimes;
    
    @Data
    public static class Coordinates {
        private Double latitude;
        private Double longitude;
    }
    
    @Data
    public static class SunTimes {
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalDateTime sunrise;
        
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalDateTime sunset;
    }
}