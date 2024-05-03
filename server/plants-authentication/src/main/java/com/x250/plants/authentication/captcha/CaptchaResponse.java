package com.x250.plants.authentication.captcha;

import lombok.Data;

@Data
public class CaptchaResponse {

    private boolean success;
    private String challengeTs;
    private String hostname;
}
