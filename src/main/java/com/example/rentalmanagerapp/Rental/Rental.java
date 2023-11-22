package com.example.rentalmanagerapp.Rental;

import com.example.rentalmanagerapp.Rental.Rentee.Rentee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name="Rental", schema = "")
public class Rental {

    @Id
    private Long id;
    private String rentalAddress;
    private Rentee renter;
    private int totalTenants;

    public Rental() {

    }

    @OneToOne
    @JoinColumn(name="Renter_id", nullable = false)
    public Rentee getRenter() {
        return renter;
    }
}
