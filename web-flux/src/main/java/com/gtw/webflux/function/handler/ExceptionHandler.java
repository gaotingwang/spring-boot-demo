package com.gtw.webflux.function.handler;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-10)
public class ExceptionHandler implements WebExceptionHandler {

    /**
     * 对异常的统一全局处理
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_HTML);

        DataBuffer dataBuffer = response.bufferFactory().wrap(ex.getMessage().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
