package org.sabhriti.api.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.sabhriti.api.exception.AlreadyExistsException;
import org.sabhriti.api.exception.BadPasswordException;
import org.sabhriti.api.exception.InvalidTokenException;
import org.sabhriti.api.exception.NotFoundException;
import org.sabhriti.api.web.dto.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Order(-2)
@Component
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private final HttpStatus defaultStatus;

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer,
                                          Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode,
                                          HttpStatus defaultStatus
    ) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        this.exceptionToStatusCode = exceptionToStatusCode;
        this.defaultStatus = defaultStatus;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var error = getError(request);

        log.error("An error has been occurred", error);

        if (error instanceof AlreadyExistsException || error instanceof BadPasswordException) {
            return this.createResponse(HttpStatus.BAD_REQUEST, error);
        }

        if (error instanceof NotFoundException  || error instanceof InvalidTokenException) {
            return this.createResponse(HttpStatus.NOT_FOUND, error);
        }

        if (error instanceof Exception exception) {
            return this.createResponse(exceptionToStatusCode.getOrDefault(exception.getClass(), defaultStatus), error);
        }

        return this.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }

    private  Mono<ServerResponse> createResponse(HttpStatus httpStatus, Throwable error) {
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ErrorResponse
                        .builder()
                        .code(httpStatus.value())
                        .message(error.getMessage())
                        .build())
                );
    }
}
