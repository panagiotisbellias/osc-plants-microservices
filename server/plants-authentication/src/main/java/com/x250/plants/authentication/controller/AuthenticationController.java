package com.x250.plants.authentication.controller;

import com.x250.plants.authentication.captcha.RecaptchaService;
import com.x250.plants.authentication.dto.AuthenticationRequest;
import com.x250.plants.authentication.dto.AuthenticationResponse;
import com.x250.plants.authentication.dto.RegisterRequest;
import com.x250.plants.authentication.exception.CaptchaVerificationException;
import com.x250.plants.authentication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final RecaptchaService recaptchaService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestParam(name = "g-recaptcha-response", required = false) String recaptchaResponse,
            HttpServletRequest request,
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        log.debug("register({}, {}, {})", recaptchaResponse, request.getClass(), registerRequest.getClass());
        String ip = getIp(request);
        log.info("IP {} is retrieved", ip);
        boolean isCaptchaValid = recaptchaService.isValidCaptcha(ip, recaptchaResponse);

        if(!isCaptchaValid) {
            log.error("Captcha verification failed");
            throw new CaptchaVerificationException("Captcha verification failed"); // CustomRuntimeException
        }
        log.info("Captcha verification succeeded");
        return ResponseEntity.ok(service.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        log.debug("authenticate({})", request.getClass());
        return ResponseEntity.ok(service.authenticate(request));
    }

    private String getIp(HttpServletRequest request) {
        log.debug("getIp({})", request.getClass());
        String ip;
        try {
            ip = request.getRemoteAddr();
            if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
                log.info("Rewriting IP to local host's one");
                InetAddress hostAddress = InetAddress.getLocalHost();
                ip = hostAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            ip = "unknown";
        }
        log.info("IP comes to {}", ip);
        return ip;
    }

}
