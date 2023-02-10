package com.springboot.app.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.GET;

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**")
                .permitAll()
                .pathMatchers(GET, "/api/products",
                        "/api/items",
                        "/api/users/users",
                        "/api/items/{id}/amount/{amount}",
                        "/api/products/{id}")
                .permitAll()
                .pathMatchers(GET, "/api/users/users/{id}")
                .hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/products/**", "/api/items/**", "/api/users/users/**")
                .hasRole("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf()
                .disable()
                .build();
    }
}
