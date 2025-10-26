package com.example.lab4.service;

import com.example.lab4.client.WeatherApiClient;
import com.example.lab4.dto.request.BulkWeatherRequest;
import com.example.lab4.dto.response.WeatherResponse;
import com.example.lab4.dto.response.WeatherSummaryResponse;
import com.example.lab4.dto.response.BulkWeatherResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {
    
    private final WeatherApiClient weatherApiClient;
    private final WeatherConversionService conversionService;
    
    public WeatherService(WeatherApiClient weatherApiClient, WeatherConversionService conversionService) {
        this.weatherApiClient = weatherApiClient;
        this.conversionService = conversionService;
    }
    
    public WeatherResponse getWeatherByCity(String city) {
        var externalResponse = weatherApiClient.getWeatherByCity(city);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public WeatherResponse getWeatherByCity(String city, String units) {
        var externalResponse = weatherApiClient.getWeatherByCity(city, units);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public WeatherResponse getWeatherByCityAndCountry(String city, String countryCode) {
        var externalResponse = weatherApiClient.getWeatherByCityAndCountry(city, countryCode);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public WeatherResponse getWeatherByCityAndCountry(String city, String countryCode, String units) {
        var externalResponse = weatherApiClient.getWeatherByCityAndCountry(city, countryCode, units);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public WeatherResponse getWeatherByCoordinates(Double lat, Double lon) {
        var externalResponse = weatherApiClient.getWeatherByCoordinates(lat, lon);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public WeatherResponse getWeatherByCoordinates(Double lat, Double lon, String units) {
        var externalResponse = weatherApiClient.getWeatherByCoordinates(lat, lon, units);
        return conversionService.convertToWeatherResponse(externalResponse);
    }
    
    public List<WeatherResponse> getBulkWeather(List<String> cities) {
        return cities.stream()
                .map(this::getWeatherByCity)
                .toList();
    }
    
    public BulkWeatherResponse getBulkWeather(BulkWeatherRequest request) {
        List<WeatherResponse> weatherData = getBulkWeather(request.getCities());
        
        BulkWeatherResponse response = new BulkWeatherResponse();
        response.setRequestedCities(request.getCities());
        response.setWeatherData(weatherData.stream()
            .map(conversionService::convertToSummaryResponse)
            .toList());
        
        response.setSuccessCount(weatherData.size());
        response.setFailureCount(0);
        
        return response;
    }
    
    public WeatherSummaryResponse getWeatherSummary(String city) {
        WeatherResponse weather = getWeatherByCity(city);
        return conversionService.convertToSummaryResponse(weather);
    }
}