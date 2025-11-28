package org.example.uberprojectauthservice.controllers;

import org.apache.catalina.User;
import org.example.uberprojectauthservice.dto.PassengerDto;
import org.example.uberprojectauthservice.dto.PassengerSigupRequestDto;
import org.example.uberprojectauthservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> SignUp(@RequestBody PassengerSigupRequestDto passengerSigupRequestDto ) {
        PassengerDto response = authService.signupPassenger(passengerSigupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
