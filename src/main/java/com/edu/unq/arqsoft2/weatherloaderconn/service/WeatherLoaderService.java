package com.edu.unq.arqsoft2.weatherloaderconn.service;

import com.edu.unq.arqsoft2.weatherloaderconn.config.WebClientBase;
import com.edu.unq.arqsoft2.weatherloaderconn.dto.AuthDto;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class WeatherLoaderService extends WebClientBase {
    @Autowired
    private AuthDto authDto;

    public WeatherLoaderService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "https://api.openweathermap.org/data/2.5");
    }

    @Retry(name = "externalApiRetry", fallbackMethod = "fallbackMethod")
    public Mono<String> getWeatherInfo(Map<String, String> queryParams) {
        queryParams.put("appid", authDto.getApiKey());
        return get("/weather", queryParams);
    }

    @Retry(name = "externalApiRetry", fallbackMethod = "fallbackMethod")
    public Mono<String> createFlightOffersSearchPricing(String requestBody) {

        return post("v1/shopping/flight-offers/pricing", null, requestBody);
    }

    private Mono<String> fallbackMethod(Exception ex) {
        return Mono.just("External api error");
    }



}
