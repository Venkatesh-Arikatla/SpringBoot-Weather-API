package com.example.lab4.exception;

public enum WeatherErrorCode {
    
    // Validation Errors (400)
    VALIDATION_CITY_EMPTY("WEATHER_001", "City name cannot be empty", 400),
    VALIDATION_CITY_INVALID_LENGTH("WEATHER_002", "City name must be between 2 and 100 characters", 400),
    VALIDATION_COUNTRY_CODE_INVALID("WEATHER_003", "Country code must be 2 letters", 400),
    VALIDATION_COORDINATES_INVALID("WEATHER_004", "Invalid coordinates provided", 400),
    VALIDATION_LATITUDE_INVALID("WEATHER_005", "Latitude must be between -90 and 90", 400),
    VALIDATION_LONGITUDE_INVALID("WEATHER_006", "Longitude must be between -180 and 180", 400),
    VALIDATION_UNITS_INVALID("WEATHER_007", "Units must be metric, imperial, or standard", 400),
    VALIDATION_CITIES_EMPTY("WEATHER_008", "Cities list cannot be empty", 400),
    VALIDATION_TOO_MANY_CITIES("WEATHER_009", "Maximum 10 cities allowed per request", 400),
    
    // External API Errors (502)
    EXTERNAL_API_UNAVAILABLE("WEATHER_101", "Weather service is temporarily unavailable", 502),
    EXTERNAL_API_TIMEOUT("WEATHER_102", "Weather service request timed out", 504),
    EXTERNAL_API_RATE_LIMITED("WEATHER_103", "Rate limit exceeded for weather service", 429),
    EXTERNAL_API_INVALID_KEY("WEATHER_104", "Invalid API key for weather service", 401),
    
    // Data Errors (404)
    CITY_NOT_FOUND("WEATHER_201", "City not found", 404),
    WEATHER_DATA_NOT_FOUND("WEATHER_203", "Weather data not available for this location", 404),
    
    // Internal Server Errors (500)
    INTERNAL_SERVER_ERROR("WEATHER_500", "Internal server error while processing weather request", 500),
    UNEXPECTED_ERROR("WEATHER_503", "Unexpected error occurred", 500);
    
    private final String code;
    private final String message;
    private final int httpStatus;
    
    WeatherErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getHttpStatus() { return httpStatus; }
}