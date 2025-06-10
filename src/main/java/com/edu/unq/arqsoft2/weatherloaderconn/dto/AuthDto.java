package com.edu.unq.arqsoft2.weatherloaderconn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@NoArgsConstructor
@Component
public class AuthDto extends DtoBase {
    @Value("${client.connector.weather.key}")
    private String apiKey;
}
