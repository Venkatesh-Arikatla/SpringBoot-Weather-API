# 🌦️ WeatherVerse Pro API

A robust Spring Boot REST API that provides real-time weather information for cities worldwide. Built with modern Java technologies and featuring a beautiful React frontend.

![Weather API](https://img.shields.io/badge/Weather-API-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 🚀 Features

- **Real-time Weather Data** - Get current weather conditions for any city
- **Global Coverage** - Support for cities worldwide
- **Multiple Search Options** - Search by city name or postal code
- **Comprehensive Data** - Temperature, humidity, wind, pressure, and more
- **Sunrise/Sunset Times** - Daily solar information
- **Geolocation Support** - Coordinates and timezone data
- **RESTful API** - Clean JSON responses
- **CORS Enabled** - Ready for web applications
- **HTTPS Ready** - Secure frontend integration

## 📋 API Endpoints

### Get Weather by City Name
```http
GET /api/weather/city/{cityName}