package com.hblog.apigateway.filter;

import com.hblog.apigateway.jwt.JwtTokenValid;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class RefreshTokenHeaderFilter extends AbstractGatewayFilterFactory<RefreshTokenHeaderFilter.Config> {

    private final JwtTokenValid jwtTokenValid;

    public RefreshTokenHeaderFilter(JwtTokenValid jwtTokenValid) {
        super(Config.class);
        this.jwtTokenValid = jwtTokenValid;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey("RefreshToken")) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = Objects.requireNonNull(request.getHeaders().get("RefreshToken")).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");
            if (!jwtTokenValid.isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            Claims claims = jwtTokenValid.parseJwt(jwt);
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("userIdx", (String) claims.get("sub"))
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }



    public static class Config {

    }
}
