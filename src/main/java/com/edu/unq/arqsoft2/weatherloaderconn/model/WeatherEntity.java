package com.edu.unq.arqsoft2.weatherloaderconn.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Data
@Document(collection = "weather_records")
public class WeatherEntity {
    @Id
    private UUID id;
    private Instant createdAt;
    private Double longitude;
    private Double latitude;
    private String cityName;
    private String description;
    private Double temperature;
}

/*
< >
{
    "coord": {
        "lon": -58.3772,
        "lat": -34.6132
    },
    "weather": [
        {
            "id": 800,
            "main": "Clear",
            "description": "clear sky",
            "icon": "01n"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 13.17,
        "feels_like": 12.68,
        "temp_min": 10.82,
        "temp_max": 13.86,
        "pressure": 1025,
        "humidity": 82,
        "sea_level": 1025,
        "grnd_level": 1024
    },
    "visibility": 10000,
    "wind": {
        "speed": 4.92,
        "deg": 120,
        "gust": 0
    },
    "clouds": {
        "all": 0
    },
    "dt": 1749680766,
    "sys": {
        "type": 2,
        "id": 2092396,
        "country": "AR",
        "sunrise": 1749639434,
        "sunset": 1749674962
    },
    "timezone": -10800,
    "id": 3435910,
    "name": "Buenos Aires",
    "cod": 200
}


 */