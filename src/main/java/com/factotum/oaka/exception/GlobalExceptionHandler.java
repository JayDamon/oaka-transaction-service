package com.factotum.oaka.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@Configuration
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @NonNull
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, @NonNull Throwable throwable) {

        log.error(throwable.getMessage(), throwable);

        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();

        if (badRequestException(throwable)) {

            return handleRequest(serverWebExchange, throwable, bufferFactory, HttpStatus.BAD_REQUEST);
        }

        if (notFoundExceptionType(throwable)) {
            return handleRequest(serverWebExchange, throwable, bufferFactory, HttpStatus.NOT_FOUND);
        }

        serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());

        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private boolean notFoundExceptionType(Throwable throwable) {
        return throwable instanceof NoSuchElementException;
    }

    private boolean badRequestException(Throwable throwable) {
        return throwable instanceof ConstraintViolationException
                || throwable instanceof IllegalArgumentException
                || throwable instanceof ServerWebInputException;
    }

    private Mono<Void> handleRequest(ServerWebExchange serverWebExchange, @NonNull Throwable throwable, DataBufferFactory bufferFactory, HttpStatus badRequest) {
        serverWebExchange.getResponse().setStatusCode(badRequest);
        DataBuffer dataBuffer = bufferFactory.wrap(throwable.getMessage().getBytes());
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

}
