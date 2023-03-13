package org.sabhriti.api.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Map;

@Configuration
@EnableWebFlux
@EnableConfigurationProperties(WebProperties.class)
public class WebfluxConfig {

    @Bean
    public HttpStatus defaultStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(RuntimeException.class, HttpStatus.BAD_REQUEST);
    }
}
