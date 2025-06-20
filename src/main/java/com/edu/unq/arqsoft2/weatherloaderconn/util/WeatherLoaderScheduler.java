package com.edu.unq.arqsoft2.weatherloaderconn.util;

import com.edu.unq.arqsoft2.weatherloaderconn.service.WeatherLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        weatherLoaderService.getWeatherInfo(params);
    }
}
