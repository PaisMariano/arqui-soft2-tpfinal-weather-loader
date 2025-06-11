package com.edu.unq.arqsoft2.weatherloaderconn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherLoaderConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherLoaderConnectorApplication.class, args);
	}

}
