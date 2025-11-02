package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequest;
import com.pm.authservice.dto.LoginResponse;
import com.pm.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> tokenOptional = authService.authenticate(loginRequest);

        if (tokenOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponse(token));
    }


    @GetMapping("/validate")
    public ResponseEntity<LoginResponse> validate(@RequestHeader("Authorization") String authHeader) {
        if (authHeader!= null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return authService.validateToken(token)? ResponseEntity.ok().build() :
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
