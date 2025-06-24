package com.edu.unq.arqsoft2.weatherloaderconn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "loki.url=http://dummy:3100/loki/api/v1/push",
    "management.endpoints.enabled-by-default=false",
    "management.endpoint.health.enabled=false",
    "management.endpoint.metrics.enabled=false",
    "management.metrics.export.prometheus.enabled=false",
    "spring.autoconfigure.exclude=org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration,org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration,org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration"
})
class ApiconnectorApplicationTests {

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }
}
