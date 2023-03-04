package org.sabhriti.api.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class CORSFilter implements WebFluxConfigurer {

	@Value("${security.cors.allowed-headers}")
	private String allowedHeaders;

	@Value("${security.cors.allowed-methods}")
	private String allowedMethods;

	@Value("${security.cors.allowed-origins}")
	private String allowedOrigins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowCredentials(false)
				.allowedOrigins(this.allowedOrigins)
				.allowedHeaders(this.allowedHeaders)
				.allowedMethods(this.allowedMethods)
				.exposedHeaders(HttpHeaders.SET_COOKIE);
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		var corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader(this.allowedHeaders);
		corsConfiguration.addAllowedMethod(this.allowedMethods);
		corsConfiguration.addAllowedOrigin(this.allowedOrigins);
		corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
		var corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(corsConfigurationSource);
	}
}