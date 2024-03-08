package com.x250.authenticationservice.controller;

import com.x250.authenticationservice.dto.AuthenticationRequest;
import com.x250.authenticationservice.dto.AuthenticationResponse;
import com.x250.authenticationservice.dto.RegisterRequest;
import com.x250.authenticationservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody  RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestParam String token
    ) {
        return ResponseEntity.ok(service.validateToken(token));
    }
}
