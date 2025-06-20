package com.edu.unq.arqsoft2.weatherloaderconn.service;

import com.edu.unq.arqsoft2.weatherloaderconn.dto.TemperatureDto;
import com.edu.unq.arqsoft2.weatherloaderconn.model.WeatherEntity;
import com.edu.unq.arqsoft2.weatherloaderconn.repository.WeatherMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class WeatherService {
    @Autowired
    private WeatherMongoRepository weatherRepository;

    public WeatherEntity saveWeatherData(WeatherEntity weatherData) {
        // Save the weather data to MongoDB
        return weatherRepository.save(weatherData);
    }

    public TemperatureDto getCurrentTemperature() {
        // Retrieve the current weather data from MongoDB
        WeatherEntity currentWeather = weatherRepository.findTopByOrderByCreatedAtDesc();

        return new TemperatureDto(currentWeather.getTemperature());
    }

    public TemperatureDto getAverageTemperatureByDay() {
        // Calculate the average temperature for the current day
        ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");
        ZonedDateTime nowInZone = ZonedDateTime.now(zoneId);
        Instant lastDay = nowInZone.toInstant().minus(1, ChronoUnit.DAYS);
        Double averageTemperature = weatherRepository.findAverageTemperatureSince(lastDay);

        return new TemperatureDto(Math.round(averageTemperature * 100.0) / 100.0);
    }

    public TemperatureDto getAverageTemperatureByWeek() {
        // Calculate the average temperature for the current week
        ZoneId zoneId = ZoneId.of("America/Argentina/Buenos_Aires");
        ZonedDateTime nowInZone = ZonedDateTime.now(zoneId);
        Instant sevenDays = nowInZone.toInstant().minus(7, ChronoUnit.DAYS);
        Double averageTemperature = weatherRepository.findAverageTemperatureSince(sevenDays);

        return new TemperatureDto(Math.round(averageTemperature * 100.0) / 100.0);
    }
}
