package com.edu.unq.arqsoft2.weatherloaderconn.exception;

public class NotFoundException extends ApiException {
    public NotFoundException(String responseBody) {
        super(404, responseBody);
    }
}

