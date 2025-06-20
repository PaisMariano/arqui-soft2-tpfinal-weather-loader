package com.edu.unq.arqsoft2.weatherloaderconn.controller;

import com.edu.unq.arqsoft2.weatherloaderconn.dto.TemperatureDto;
import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import com.edu.unq.arqsoft2.weatherloaderconn.service.WeatherLoaderService;
import com.edu.unq.arqsoft2.weatherloaderconn.service.WeatherService;
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
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/search/current")
    public TemperatureDto getCurrentWeather() {
        return service.getCurrentTemperature();
    }

    @GetMapping("/search/avg/day")
    public TemperatureDto getAverageTemperatureByDay() {
        return service.getAverageTemperatureByDay();
    }

    @GetMapping("/search/avg/week")
    public TemperatureDto getAverageTemperatureByWeek() {
        return service.getAverageTemperatureByWeek();
    }

}
