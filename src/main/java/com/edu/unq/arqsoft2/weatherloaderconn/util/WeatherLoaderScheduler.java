package com.edu.unq.arqsoft2.weatherloaderconn.util;

import com.edu.unq.arqsoft2.weatherloaderconn.service.WeatherLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WeatherLoaderScheduler {

    @Autowired
    private WeatherLoaderService weatherLoaderService;

    // Runs every 5 minutes
    @Scheduled(fixedRate = 60000 * 5)
    public void fetchWeatherPeriodically() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "Buenos Aires"); // Example city
        params.put("units", "metric");
        
        Mono.fromFuture(weatherLoaderService.getWeatherInfo(params))
            .doOnError(e -> log.error("Error al obtener datos meteorológicos", e))
            .subscribe(
                null, // onNext no es necesario para Mono<Void>
                e -> log.error("Error en la tarea programada", e),
                () -> log.debug("Tarea de obtención de datos meteorológicos completada")
            );
    }
}
