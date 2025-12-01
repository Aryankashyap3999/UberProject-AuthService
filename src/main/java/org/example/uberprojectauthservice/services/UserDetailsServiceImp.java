package org.example.uberprojectauthservice.services;

import org.example.uberprojectauthservice.helper.AuthPassengerDetails;
import org.example.uberprojectauthservice.models.Passenger;
import org.example.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImp(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findByEmail(email);
        if (passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        } else {
            throw new UsernameNotFoundException("Can't find the user with this email");
        }

    }


}
