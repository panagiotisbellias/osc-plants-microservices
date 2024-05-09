package com.x250.plants.authentication.captcha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RecaptchaConfigurationTest {

    @InjectMocks
    RecaptchaConfiguration recaptchaConfiguration;

    @Test
    void testRestTemplate() {
        Assertions.assertInstanceOf(RestTemplate.class, recaptchaConfiguration.restTemplate());
    }

}
