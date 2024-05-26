package com.x250.plants.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
