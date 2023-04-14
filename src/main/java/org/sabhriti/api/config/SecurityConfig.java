package org.sabhriti.api.config;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.service.security.AuthenticationManager;
import org.sabhriti.api.service.security.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final static List<String> UNSECURED_PATHS = List.of(
            "/security/login/**",
            "/security/signup/**",
            "/security/create-password/**",
            "/security/forget-password/**",
            "/user-token/validate/**",
            "/v2/api-docs",
            "/swagger-ui**",
            "/swagger-resources/**",
            "/*/swagger-resources/**",
            "/*/v2/api-docs"
    );

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        var authorizeExchangeSpec = http.cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange();

        UNSECURED_PATHS.forEach(path -> authorizeExchangeSpec.pathMatchers(path).permitAll());

        return authorizeExchangeSpec
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
