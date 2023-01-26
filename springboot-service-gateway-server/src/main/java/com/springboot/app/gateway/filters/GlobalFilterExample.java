package com.springboot.app.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Slf4j
@Component
public class GlobalFilterExample implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Executing pre filter");
        exchange.getRequest().mutate().headers(header -> header.add("token", "123456"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Executing post filter");
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
                    .ifPresent(value -> exchange.getResponse().getHeaders().add("token", value));
            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
            //exchange.getResponse().getHeaders().setContentType(TEXT_PLAIN);
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
