package com.x250.plants.authentication.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaptchaVerificationException extends RuntimeException{

    public CaptchaVerificationException(String message) {
        super(message);
        log.error(message);
    }
}
