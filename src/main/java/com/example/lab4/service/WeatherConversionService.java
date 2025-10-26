package com.example.lab4.service;

import com.example.lab4.dto.external.WeatherApiResponse;
import com.example.lab4.dto.response.WeatherResponse;
import com.example.lab4.dto.response.WeatherSummaryResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WeatherConversionService {
    
    public WeatherResponse convertToWeatherResponse(WeatherApiResponse externalResponse) {
        WeatherResponse response = new WeatherResponse();
        response.setCity(externalResponse.getName());
        response.setCountry(externalResponse.getSys() != null ? externalResponse.getSys().getCountry() : null);
        response.setTemperature(externalResponse.getMain() != null ? externalResponse.getMain().getTemp() : null);
        response.setFeelsLike(externalResponse.getMain() != null ? externalResponse.getMain().getFeelsLike() : null);
        response.setDescription(externalResponse.getWeather() != null && !externalResponse.getWeather().isEmpty() 
            ? externalResponse.getWeather().get(0).getDescription() : null);
        response.setWeatherCondition(externalResponse.getWeather() != null && !externalResponse.getWeather().isEmpty() 
            ? externalResponse.getWeather().get(0).getMain() : null);
        response.setHumidity(externalResponse.getMain() != null ? externalResponse.getMain().getHumidity() : null);
        response.setPressure(externalResponse.getMain() != null ? externalResponse.getMain().getPressure() : null);
        response.setWindSpeed(externalResponse.getWind() != null ? externalResponse.getWind().getSpeed() : null);
        response.setVisibility(externalResponse.getVisibility());
        response.setIcon(externalResponse.getWeather() != null && !externalResponse.getWeather().isEmpty() 
            ? externalResponse.getWeather().get(0).getIcon() : null);
        response.setTimestamp(LocalDateTime.now());
        
        // Set coordinates
        if (externalResponse.getCoord() != null) {
            WeatherResponse.Coordinates coordinates = new WeatherResponse.Coordinates();
            coordinates.setLatitude(externalResponse.getCoord().getLat());
            coordinates.setLongitude(externalResponse.getCoord().getLon());
            response.setCoordinates(coordinates);
        }
        
        // Set sun times
        if (externalResponse.getSys() != null) {
            WeatherResponse.SunTimes sunTimes = new WeatherResponse.SunTimes();
            if (externalResponse.getSys().getSunrise() != null) {
                sunTimes.setSunrise(LocalDateTime.ofEpochSecond(externalResponse.getSys().getSunrise(), 0, ZoneOffset.UTC));
            }
            if (externalResponse.getSys().getSunset() != null) {
                sunTimes.setSunset(LocalDateTime.ofEpochSecond(externalResponse.getSys().getSunset(), 0, ZoneOffset.UTC));
            }
            response.setSunTimes(sunTimes);
        }
        
        return response;
    }
    
    public WeatherSummaryResponse convertToSummaryResponse(WeatherResponse weather) {
        WeatherSummaryResponse summary = new WeatherSummaryResponse();
        summary.setCity(weather.getCity());
        summary.setCountry(weather.getCountry());
        summary.setTemperature(weather.getTemperature());
        summary.setFeelsLike(weather.getFeelsLike());
        summary.setDescription(weather.getDescription());
        summary.setIcon(weather.getIcon());
        summary.setTimestamp(weather.getTimestamp());
        return summary;
    }
}