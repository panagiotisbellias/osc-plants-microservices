package com.x250.plants.authentication.controller;

import com.x250.plants.authentication.captcha.RecaptchaService;
import com.x250.plants.authentication.dto.AuthenticationRequest;
import com.x250.plants.authentication.dto.RegisterRequest;
import com.x250.plants.authentication.exception.CaptchaVerificationException;
import com.x250.plants.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService service;

    @Mock
    RecaptchaService recaptchaService;

    @Mock
    HttpServletRequest request;

    @Mock
    RegisterRequest registerRequest;

    @Test
    void testRegister() {
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        Mockito.when(recaptchaService.isValidCaptcha(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
        Assertions.assertEquals(ResponseEntity.ok(service.register(registerRequest)), authenticationController.register("recaptchaResponse", request, registerRequest));
    }

    @Test
    void testRegisterIsCaptchaValidFalse() {
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        CaptchaVerificationException captchaVerificationException =  Assertions.assertThrows(CaptchaVerificationException.class, () -> authenticationController.register("recaptchaResponse", request, registerRequest));
        Assertions.assertEquals("Captcha verification failed", captchaVerificationException.getMessage());
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request = Mockito.mock(AuthenticationRequest.class);
        Assertions.assertEquals(ResponseEntity.ok(service.authenticate(request)), authenticationController.authenticate(request));
    }

}
