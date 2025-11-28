package org.example.uberprojectauthservice.services;

import org.example.uberprojectauthservice.dto.PassengerDto;
import org.example.uberprojectauthservice.dto.PassengerSigupRequestDto;
import org.example.uberprojectauthservice.models.Passenger;
import org.example.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSigupRequestDto passengerSigupRequestDto) {
        Passenger passenger = Passenger.builder()
                .name(passengerSigupRequestDto.getName())
                .email(passengerSigupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(passengerSigupRequestDto.getPassword()))
                .phoneNumber(passengerSigupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDto.fromPassenger(newPassenger);

    }
}
