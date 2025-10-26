package com.example.lab4.dto.request;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CoordinatesRequest {
    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double lat;
    
    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double lon;
    
    @Pattern(regexp = "^(metric|imperial|standard)$", message = "Units must be metric, imperial, or standard")
    private String units = "metric";
}