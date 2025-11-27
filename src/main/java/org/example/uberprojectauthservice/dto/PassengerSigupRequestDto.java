package org.example.uberprojectauthservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSigupRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
}
