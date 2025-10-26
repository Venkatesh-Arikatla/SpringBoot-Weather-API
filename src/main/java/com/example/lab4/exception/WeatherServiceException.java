package com.example.lab4.exception;

import lombok.Getter;

@Getter
public class WeatherServiceException extends RuntimeException {
    private final WeatherErrorCode errorCode;
    private final String details;
    private final Object requestData;
    
    public WeatherServiceException(WeatherErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = null;
        this.requestData = null;
    }
    
    public WeatherServiceException(WeatherErrorCode errorCode, String details) {
        super(errorCode.getMessage() + (details != null ? ": " + details : ""));
        this.errorCode = errorCode;
        this.details = details;
        this.requestData = null;
    }
    
    public WeatherServiceException(WeatherErrorCode errorCode, String details, Object requestData) {
        super(errorCode.getMessage() + (details != null ? ": " + details : ""));
        this.errorCode = errorCode;
        this.details = details;
        this.requestData = requestData;
    }
    
    public WeatherServiceException(WeatherErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.details = cause.getMessage();
        this.requestData = null;
    }
    
    public int getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}