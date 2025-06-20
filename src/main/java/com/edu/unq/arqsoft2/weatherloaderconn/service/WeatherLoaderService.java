package com.edu.unq.arqsoft2.weatherloaderconn.service;

import com.edu.unq.arqsoft2.weatherloaderconn.config.WebClientBase;
import com.edu.unq.arqsoft2.weatherloaderconn.dto.AuthDto;
import com.edu.unq.arqsoft2.weatherloaderconn.exception.MappingException;
import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import com.edu.unq.arqsoft2.weatherloaderconn.repository.WeatherMongoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class WeatherLoaderService extends WebClientBase {
    @Autowired
    private AuthDto authDto;
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    public WeatherLoaderService(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "https://api.openweathermap.org/data/2.5");
    }

    @Retry(name = "externalApiRetry", fallbackMethod = "fallbackMethod")
    @CircuitBreaker(name = "weatherService", fallbackMethod = "fallbackWeatherInfo")
    public void getWeatherInfo(Map<String, String> queryParams) {
        queryParams.put("appid", authDto.getApiKey());

        Mono<WeatherEntity> weatherEntityMono = get("/weather", queryParams).map(this::mapToWeatherEntity);

        WeatherEntity entity = weatherService.saveWeatherData(weatherEntityMono.block());
        System.out.println("Weather data saved successfully, temp: " +entity.getTemperature() + " date: " + new Date());
    }

    @Retry(name = "externalApiRetry", fallbackMethod = "fallbackMethod")
    public Mono<String> method(String requestBody) {

        return post("v1/", null, requestBody);
    }

    public String fallbackWeatherInfo(Map<String, String> queryParams, Throwable t) {
        return "Service unavailable. Please try again later.";
    }

    private Mono<String> fallbackMethod(Exception ex) {
        return Mono.just("External api error");
    }

    private WeatherEntity mapToWeatherEntity(String jsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);

            Double lon = root.path("coord").path("lon").asDouble();
            Double lat = root.path("coord").path("lat").asDouble();
            String city = root.path("name").asText();
            String description = root.path("weather").get(0).path("description").asText();
            Double temp = root.path("main").path("temp").asDouble();

            WeatherEntity entity = new WeatherEntity();
            entity.setId(UUID.randomUUID());

            ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");
            ZonedDateTime nowInZone = ZonedDateTime.now(zoneId);
            entity.setCreatedAt(nowInZone.toInstant());

            entity.setLongitude(lon);
            entity.setLatitude(lat);
            entity.setCityName(city);
            entity.setDescription(description);
            entity.setTemperature(temp);

            return entity;

        } catch (Exception e) {
            throw new MappingException("Error mapping JSON response");
        }
    }

}
