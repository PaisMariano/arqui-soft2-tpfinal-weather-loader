package com.edu.unq.arqsoft2.weatherloaderconn.util;

import java.time.Instant;

public class TokenInfo {
    private final String token;
    private final Instant expiration;

    public TokenInfo(String token, Instant expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() { return token; }

    public boolean isExpired() {
        return Instant.now().isAfter(expiration.minusSeconds(10));
    }
}
