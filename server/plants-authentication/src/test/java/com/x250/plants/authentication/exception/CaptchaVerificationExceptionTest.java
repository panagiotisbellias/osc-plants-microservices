package com.x250.plants.authentication.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CaptchaVerificationExceptionTest {

    @Test
    void testConstructor() {
        CaptchaVerificationException captchaVerificationException = new CaptchaVerificationException("message");
        Assertions.assertInstanceOf(CaptchaVerificationException.class, captchaVerificationException);
        Assertions.assertEquals("message", captchaVerificationException.getMessage());
    }

}
