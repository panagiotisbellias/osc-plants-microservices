package com.x250.authenticationservice.captcha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RecaptchaServiceTest {

    @InjectMocks
    RecaptchaService recaptchaService;

    @Mock
    RestTemplate restTemplate;

    @Test
    void testConstructor() {
        RecaptchaService recaptchaService = new RecaptchaService(new RestTemplate());
        Assertions.assertInstanceOf(RecaptchaService.class, recaptchaService);
    }

    @Test
    void testIsValidCaptcha() {
        CaptchaResponse captchaResponse = Mockito.mock(CaptchaResponse.class);
        Mockito.when(captchaResponse.isSuccess()).thenReturn(true);
        Mockito.when(restTemplate.postForObject(ArgumentMatchers.anyString(), ArgumentMatchers.eq(null), ArgumentMatchers.eq(CaptchaResponse.class))).thenReturn(captchaResponse);
        Assertions.assertTrue(recaptchaService.isValidCaptcha("ip", "recaptchaResponse"));
    }

    @Test
    void testIsValidCaptchaRecaptchaResponseInvalid() {
        RecaptchaService recaptchaService = new RecaptchaService(new RestTemplate());
        Assertions.assertFalse(recaptchaService.isValidCaptcha("ip", ""));
    }

    @Test
    void testIsValidCaptchaResponseIsNull() {
        Mockito.when(restTemplate.postForObject(ArgumentMatchers.anyString(), ArgumentMatchers.eq(null), ArgumentMatchers.eq(CaptchaResponse.class))).thenReturn(null);
        Assertions.assertFalse(recaptchaService.isValidCaptcha("ip", "recaptchaResponse"));
    }

}
