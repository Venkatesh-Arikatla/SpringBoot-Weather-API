package com.example.lab4.util;

import com.example.lab4.exception.WeatherServiceException;
import com.example.lab4.exception.WeatherErrorCode;

public class WeatherValidator {
    
    public static void validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new WeatherServiceException(WeatherErrorCode.VALIDATION_CITY_EMPTY);
        }
        
        String cleanCity = city.trim();
        if (cleanCity.length() < 2 || cleanCity.length() > 100) {
            throw new WeatherServiceException(
                WeatherErrorCode.VALIDATION_CITY_INVALID_LENGTH,
                "City: " + cleanCity
            );
        }
    }
    
    public static void validateCoordinates(Double lat, Double lon) {
        if (lat == null || lon == null) {
            throw new WeatherServiceException(WeatherErrorCode.VALIDATION_COORDINATES_INVALID);
        }
        
        if (lat < -90 || lat > 90) {
            throw new WeatherServiceException(
                WeatherErrorCode.VALIDATION_LATITUDE_INVALID,
                "Latitude: " + lat
            );
        }
        
        if (lon < -180 || lon > 180) {
            throw new WeatherServiceException(
                WeatherErrorCode.VALIDATION_LONGITUDE_INVALID,
                "Longitude: " + lon
            );
        }
    }
    
    public static void validateCountryCode(String countryCode) {
        if (countryCode != null && !countryCode.isEmpty()) {
            if (!countryCode.matches("^[A-Za-z]{2}$")) {
                throw new WeatherServiceException(
                    WeatherErrorCode.VALIDATION_COUNTRY_CODE_INVALID,
                    "Country code: " + countryCode
                );
            }
        }
    }
    
    public static void validateUnits(String units) {
        if (units != null && !units.isEmpty()) {
            if (!units.matches("^(metric|imperial|standard)$")) {
                throw new WeatherServiceException(
                    WeatherErrorCode.VALIDATION_UNITS_INVALID,
                    "Units: " + units
                );
            }
        }
    }
    
    public static void validateCitiesList(java.util.List<String> cities) {
        if (cities == null || cities.isEmpty()) {
            throw new WeatherServiceException(WeatherErrorCode.VALIDATION_CITIES_EMPTY);
        }
        
        if (cities.size() > 10) {
            throw new WeatherServiceException(
                WeatherErrorCode.VALIDATION_TOO_MANY_CITIES,
                "Provided: " + cities.size() + ", Maximum: 10"
            );
        }
        
        for (String city : cities) {
            validateCity(city);
        }
    }
}