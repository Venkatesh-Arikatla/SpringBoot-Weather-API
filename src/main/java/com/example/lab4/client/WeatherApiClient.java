package com.example.lab4.client;

import com.example.lab4.dto.external.WeatherApiResponse;
import com.example.lab4.exception.WeatherServiceException;
import com.example.lab4.exception.WeatherErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WeatherApiClient {
    
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiClient.class);
    
    private final RestTemplate restTemplate;
    
    @Value("${weather.api.base-url:https://api.openweathermap.org/data/2.5}")
    private String apiUrl;
    
    @Value("${weather.api.key:demo}")
    private String apiKey;
    
    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public WeatherApiResponse getWeatherByCity(String city) {
        try {
            String url = buildWeatherUrl(city, "metric");
            logger.info("Calling Weather API for city: {}", city);
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Successfully fetched weather data for city: {}", city);
                return response.getBody();
            } else {
                logger.error("Weather API returned non-success status: {}", response.getStatusCode());
                throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for city: {} - {}", city, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    public WeatherApiResponse getWeatherByCityAndCountry(String city, String countryCode) {
        try {
            String query = city + "," + countryCode;
            String url = buildWeatherUrl(query, "metric");
            logger.info("Calling Weather API for city: {}, country: {}", city, countryCode);
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new WeatherServiceException(WeatherErrorCode.CITY_NOT_FOUND);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for city: {}, country: {} - {}", city, countryCode, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    public WeatherApiResponse getWeatherByCoordinates(Double lat, Double lon) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/weather")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .toUriString();
            
            logger.info("Calling Weather API for coordinates: lat={}, lon={}", lat, lon);
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new WeatherServiceException(WeatherErrorCode.WEATHER_DATA_NOT_FOUND);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for coordinates: lat={}, lon={} - {}", lat, lon, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    public WeatherApiResponse getWeatherByCity(String city, String units) {
        try {
            String url = buildWeatherUrl(city, units);
            logger.info("Calling Weather API for city: {} with units: {}", city, units);
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for city: {} with units: {} - {}", city, units, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    public WeatherApiResponse getWeatherByCityAndCountry(String city, String countryCode, String units) {
        try {
            String query = city + "," + countryCode;
            String url = buildWeatherUrl(query, units);
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new WeatherServiceException(WeatherErrorCode.CITY_NOT_FOUND);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for city: {}, country: {} with units: {} - {}", 
                       city, countryCode, units, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    public WeatherApiResponse getWeatherByCoordinates(Double lat, Double lon, String units) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/weather")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid", apiKey)
                    .queryParam("units", units)
                    .toUriString();
            
            ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new WeatherServiceException(WeatherErrorCode.WEATHER_DATA_NOT_FOUND);
            }
            
        } catch (Exception e) {
            logger.error("Error fetching weather for coordinates: lat={}, lon={} with units: {} - {}", 
                       lat, lon, units, e.getMessage());
            throw new WeatherServiceException(WeatherErrorCode.EXTERNAL_API_UNAVAILABLE, e.getMessage());
        }
    }
    
    private String buildWeatherUrl(String query, String units) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl + "/weather")
                .queryParam("q", query)
                .queryParam("appid", apiKey)
                .queryParam("units", units)
                .toUriString();
    }
}