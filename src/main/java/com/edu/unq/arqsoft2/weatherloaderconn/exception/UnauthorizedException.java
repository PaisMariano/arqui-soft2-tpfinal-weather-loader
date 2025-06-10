package com.edu.unq.arqsoft2.weatherloaderconn.exception;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String responseBody) {
        super(401, responseBody);
    }
}
