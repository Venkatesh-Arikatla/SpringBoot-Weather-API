package com.example.lab4.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String name;
    private Integer visibility;
    private Integer timezone;
    private Long dt;
    
    @Data
    public static class Coord {
        private Double lon;
        private Double lat;
    }
    
    @Data
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
    
    @Data
    public static class Main {
        private Double temp;
        @JsonProperty("feels_like")
        private Double feelsLike;
        private Integer pressure;
        private Integer humidity;
        @JsonProperty("temp_min")
        private Double tempMin;
        @JsonProperty("temp_max")
        private Double tempMax;
    }
    
    @Data
    public static class Wind {
        private Double speed;
        private Integer deg;
        private Double gust;
    }
    
    @Data
    public static class Sys {
        private String country;
        private Long sunrise;
        private Long sunset;
    }
}