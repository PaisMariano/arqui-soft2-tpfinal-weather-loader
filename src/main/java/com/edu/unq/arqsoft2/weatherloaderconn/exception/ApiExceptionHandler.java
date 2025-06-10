package com.edu.unq.arqsoft2.weatherloaderconn.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> manejarApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBody());
    }

}
