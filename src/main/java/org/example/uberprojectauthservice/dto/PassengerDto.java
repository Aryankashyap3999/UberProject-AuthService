package org.example.uberprojectauthservice.dto;

import lombok.*;
import org.example.uberprojectauthservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private String id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private Date createdAt;

    public static PassengerDto fromPassenger(Passenger passenger) {
        PassengerDto result = PassengerDto.builder()
                .id(passenger.getId().toString())
                .createdAt(passenger.getCreatedAt())
                .name(passenger.getName())
                .phoneNumber(passenger.getPhoneNumber())
                .email(passenger.getEmail())
                .password(passenger.getPassword())
                .phoneNumber(passenger.getPhoneNumber())
                .build();

        return result;
    }
}
