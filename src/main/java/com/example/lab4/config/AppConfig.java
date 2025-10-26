package com.example.lab4.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.base-url}")
    private String baseUrl;
    
    @Value("${weather.api.units:metric}")
    private String units;
    
    @Value("${weather.cache.freshness.minutes:30}")
    private int cacheFreshnessMinutes;
    
    @Value("${weather.bulk.max-cities:10}")
    private int bulkMaxCities;

    public String getApiKey() { return apiKey; }
    public String getBaseUrl() { return baseUrl; }
    public String getUnits() { return units; }
    public int getCacheFreshnessMinutes() { return cacheFreshnessMinutes; }
    public int getBulkMaxCities() { return bulkMaxCities; }
}