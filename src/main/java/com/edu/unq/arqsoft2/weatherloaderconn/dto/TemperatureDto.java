package com.edu.unq.arqsoft2.weatherloaderconn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemperatureDto {
    private Double temperature;

    public TemperatureDto() {
    }

    public TemperatureDto(Double temp) {
        if (temp != null) {
            temperature = temp;
        } else {
            temperature = 0.0;
        }
    }

}
