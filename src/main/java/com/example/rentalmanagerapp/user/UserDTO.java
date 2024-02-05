package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.units.Units;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.List;

public record UserDTO (

        Long id,

        String name,

        LocalDate birthDate,

        String address,

        String email,

        String telephone,

        String username,

        UserRoles userRoles,

        List<String> authorities,

        LocalDate rentDue,

        LocalDate rentLastPaid,

        LocalDate dateLeaseStarted,

        Long amountPaid,

        Long amountOwed,

        String rentalAddress,

        Units usersUnit

) {

}
