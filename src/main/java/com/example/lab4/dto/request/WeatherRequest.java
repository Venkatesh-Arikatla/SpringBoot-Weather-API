package com.example.lab4.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class WeatherRequest {
    
    @NotBlank(message = "City name is required")
    @Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
    private String city;
    
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "Country code must be 2 letters")
    private String countryCode;
    
    @Pattern(regexp = "^(metric|imperial|standard)$", message = "Units must be metric, imperial, or standard")
    private String units = "metric";
}