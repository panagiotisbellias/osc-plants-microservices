package com.x250.plants.authentication.exception;

public class CaptchaVerificationException extends RuntimeException{

    public CaptchaVerificationException(String message) {
        super(message);
    }
}
