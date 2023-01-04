package com.springboot.app.gateway.filters.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Configuration> {

    public ExampleGatewayFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            log.info("Executing pre gateway filter factory: " + config.message);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Optional.ofNullable(config.cookieValue).ifPresent(cookie -> exchange.getResponse()
                        .addCookie(ResponseCookie.from(config.cookieName, cookie).build()));
                log.info("Executing post gateway filter factory: " + config.message);
            }));
        };
    }

    @Override
    public String name() {
        return "ExampleCookie";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "cookieName", "cookieValue");
    }

    @Getter
    @Setter
    public static class Configuration {
        private String message;
        private String cookieValue;
        private String cookieName;
    }
}
