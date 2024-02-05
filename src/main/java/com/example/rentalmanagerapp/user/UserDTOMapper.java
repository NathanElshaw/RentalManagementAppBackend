package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user){
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getBirthDate(),
                user.getAddress(),
                user.getEmail(),
                user.getTelephone(),
                user.getUsername(),
                user.getUserRole(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                user.getRentDue(),
                user.getRentLastPaid(),
                user.getDateLeaseStarted(),
                user.getAmountPaid(),
                user.getAmountOwed(),
                user.getRentalAddress(),
                user.getUsersUnit()
        );
    }
}
