package com.example.lab4.exception;

import com.example.lab4.dto.response.WeatherErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<WeatherErrorResponse> handleWeatherServiceException(
            WeatherServiceException ex, WebRequest request) {
        
        logger.warn("Weather service exception: {} - {}", ex.getErrorCode().getCode(), ex.getMessage());
        
        if (ex.getErrorCode().getHttpStatus() >= 500) {
            logger.error("Weather service error occurred", ex);
        }
        
        WeatherErrorResponse errorResponse = new WeatherErrorResponse(
            ex.getErrorCode().getCode(),
            ex.getErrorCode().getMessage(),
            ex.getDetails()
        );
        errorResponse.setRequestData(ex.getRequestData());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WeatherErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        WeatherErrorResponse errorResponse = new WeatherErrorResponse(
            "VALIDATION_ERROR",
            errorMessage
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException ex) {
        // Silently ignore favicon.ico and other static resource not found errors
        if (!ex.getResourcePath().equals("favicon.ico")) {
            logger.debug("Resource not found: {}", ex.getResourcePath());
        }
        // Return empty response with 404 status
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<WeatherErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        // Don't log favicon-related exceptions
        if (ex.getMessage() != null && ex.getMessage().contains("favicon.ico")) {
            return ResponseEntity.notFound().build();
        }
        
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        
        WeatherErrorResponse errorResponse = new WeatherErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred",
            ex.getMessage()
        );
        
        return ResponseEntity.status(500).body(errorResponse);
    }
}