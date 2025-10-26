package com.example.lab4.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class BulkWeatherRequest {
    @NotEmpty(message = "At least one city is required")
    @Size(max = 10, message = "Maximum 10 cities allowed per request")
    private List<@NotBlank String> cities;
    
    @Pattern(regexp = "^(metric|imperial|standard)$", message = "Units must be metric, imperial, or standard")
    private String units = "metric";
}