package com.x250.plants.authentication.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
        log.error(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        log.error(super.toString());
    }

}