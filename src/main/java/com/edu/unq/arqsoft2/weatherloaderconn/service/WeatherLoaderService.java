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
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public Mono<Void> getWeatherInfo(Map<String, String> queryParams) {
        String city = queryParams.get("q");
        log.info("Iniciando solicitud de datos meteorológicos para la ciudad: {}", city);
        
        return Mono.defer(() -> {
            queryParams.put("appid", authDto.getApiKey());
            log.debug("Parámetros de consulta: {}", queryParams);
            return get("/weather", queryParams);
        })
        .doOnNext(response -> log.debug("Respuesta recibida de la API: {}", response))
        .map(this::mapToWeatherEntity)
        .map(weatherService::saveWeatherData)
        .doOnSuccess(entity -> 
            log.info("Datos meteorológicos guardados exitosamente - Ciudad: {}, Temperatura: {}°C, Fecha: {}", 
                   city, entity.getTemperature(), new Date())
        )
        .then();
    }

    @Retry(name = "externalApiRetry", fallbackMethod = "fallbackMethod")
    public Mono<String> method(String requestBody) {

        return post("v1/", null, requestBody);
    }

    public Mono<Void> fallbackWeatherInfo(Map<String, String> queryParams, Throwable t) {
        String city = queryParams != null ? queryParams.get("q") : "ciudad desconocida";
        log.warn("Fallback activado para la ciudad: {}. Razón: {}", city, t != null ? t.getMessage() : "desconocida");
        return Mono.empty();
    }

    private Mono<Void> fallbackMethod(Map<String, String> queryParams, Exception ex) {
        String city = queryParams != null ? queryParams.get("q") : "ciudad desconocida";
        log.error("Error en la llamada a la API externa para la ciudad: {}. Fallback activado", city, ex);
        return Mono.empty();
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

            log.debug("Mapeo completado exitosamente para la ciudad: {}", city);
            return entity;

        } catch (Exception e) {
            log.error("Error al mapear la respuesta JSON: " + jsonResponse, e);
            throw new MappingException("Error mapping JSON response", e);
        }
    }

}
