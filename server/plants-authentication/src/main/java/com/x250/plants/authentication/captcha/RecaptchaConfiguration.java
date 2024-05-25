package com.x250.plants.authentication.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RecaptchaConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        log.debug("restTemplate()");
        return new RestTemplate();
    }
}
