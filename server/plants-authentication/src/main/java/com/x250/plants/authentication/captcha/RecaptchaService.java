package com.x250.plants.authentication.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

@Slf4j
@Service
public class RecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidCaptcha(String ip, String recaptchaResponse) {
        log.debug("isValidCaptcha({}, {})", ip, recaptchaResponse);
        if(!responseSanityCheck(recaptchaResponse)) {
            log.warn("Recaptcha response {} doesn't match with the pattern", recaptchaResponse);
            return false;
        }

        String params = "?secret=" + recaptchaSecret + "&response=" + recaptchaResponse + "&remoteip=" + ip;
        String completeUrl = GOOGLE_RECAPTCHA_VERIFY_URL + params;
        CaptchaResponse resp = restTemplate.postForObject(completeUrl, null, CaptchaResponse.class);
        log.info("Request for captcha verification done");

        if(resp != null) {
            log.info("Response for captcha verification: {}", resp);
            return resp.isSuccess();
        }
        log.warn("Response for captcha verification is null");
        return false;
    }

    private boolean responseSanityCheck(String response) {
        log.debug("responseSanityCheck({})", response);
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

}
