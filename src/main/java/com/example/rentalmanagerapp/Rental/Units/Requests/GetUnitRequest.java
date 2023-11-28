package com.example.rentalmanagerapp.Rental.Units.Requests;

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
