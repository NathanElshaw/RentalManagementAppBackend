package com.example.rentalmanagerapp.rental.units.requests;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class GetUnitRequest {
    private Long userId;
    private String requestingUser;
}
