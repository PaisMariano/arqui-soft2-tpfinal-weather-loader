package com.edu.unq.arqsoft2.weatherloaderconn.controller;

import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import com.edu.unq.arqsoft2.weatherloaderconn.service.WeatherLoaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/weather-loader")
public class WeatherLoaderController {
    private final WeatherLoaderService service;

    public WeatherLoaderController(WeatherLoaderService service) {
        this.service = service;
    }

    /*Circuito Basico*/

    @GetMapping("/search")
    public Mono<WeatherEntity> getWeatherInfo(@RequestParam Map<String, String> queryParams) {
        return service.getWeatherInfo(queryParams);
    }

    @PostMapping("/shopping/flight-offers/pricing")
    public Mono<String> method(@RequestBody String requestBody) {
        return service.method(requestBody);
    }


}
