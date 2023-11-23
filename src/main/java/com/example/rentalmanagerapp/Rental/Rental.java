package com.example.rentalmanagerapp.Rental;

import com.example.rentalmanagerapp.Rental.Rentee.Rentee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="RENTALS")
public class Rental {
    @SequenceGenerator(
            name = "rentalSequence",
            sequenceName = "rentalSequence"
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "rentalSequence"
    )

    @Id
    private Long id;
    private String rentalAddress;
    @ManyToOne
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
