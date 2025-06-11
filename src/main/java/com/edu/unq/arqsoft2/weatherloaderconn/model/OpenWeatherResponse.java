package com.edu.unq.arqsoft2.weatherloaderconn.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenWeatherResponse {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private String name;

    @Getter
    @Setter
    public static class Coord {
        private Double lon;
        private Double lat;
    }
    @Getter
    @Setter
    public static class Weather {
        private String description;
    }

    @Getter
    @Setter
    public static class Main {
        private Double temp;
    }
}
