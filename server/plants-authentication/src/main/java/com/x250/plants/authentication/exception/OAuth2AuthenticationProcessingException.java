package com.x250.plants.authentication.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
        log.error(super.toString());
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
        log.error(msg);
    }

}