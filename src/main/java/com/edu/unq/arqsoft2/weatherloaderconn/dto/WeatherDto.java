package com.edu.unq.arqsoft2.weatherloaderconn.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto {
    private Double longitude;
    private Double latitude;
    private String cityName;
    private String description;
    private Double temperature;
}
