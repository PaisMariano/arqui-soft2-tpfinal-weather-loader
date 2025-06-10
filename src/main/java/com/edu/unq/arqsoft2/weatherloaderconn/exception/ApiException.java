package com.edu.unq.arqsoft2.weatherloaderconn.exception;

public class ApiException extends RuntimeException {
    private final Integer statusCode;
    private final String responseBody;

    public ApiException(Integer statusCode, String responseBody) {
        super("Error API - Código: " + statusCode + ", Detalle: " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

}
