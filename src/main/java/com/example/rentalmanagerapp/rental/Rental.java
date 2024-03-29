package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Entity
@Table(name="rentals")
@Getter
@Setter
public class Rental {

    @SequenceGenerator(
            name = "rentalSequence",
            sequenceName = "rentalSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "rentalSequence"
    )

    @Id
    private Long id;

    private String rentalAddress;

    private String description;

    private String type;

    private int totalTenants = 0;

    private int totalUnits = 0;

    private double avgRentAmount = 0;

    private double totalRentIncome = 0;

    @OneToOne
    private User assignedManager = null;

    @OneToOne
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public Rental(){

    }

    public Rental(String rentalAddress,
                  String description,
                  String type) {
        this.rentalAddress = rentalAddress;
        this.description = description;
        this.type = type;
    }

    public Rental(
            String rentalAddress,
            String type){
        this.rentalAddress = rentalAddress;
        this.type = type;
    }
    public Rental(
            Long id,
            String rentalAddress,
            String type){
        this.id = id;
        this.rentalAddress = rentalAddress;
        this.type = type;
    }

    public Rental(
            Long id,
            String rentalAddress,
            String description,
            String type,
            int totalTenants,
            int totalUnits,
            User assignedManager,
            User createdBy,
            LocalDateTime updatedAt) {
        this.id = id;
        this.rentalAddress = rentalAddress;
        this.description = description;
        this.type = type;
        this.totalTenants = totalTenants;
        this.totalUnits = totalUnits;
        this.assignedManager = assignedManager;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
    }

    @AllArgsConstructor
    @Getter
    public static class Dto{
        private final Rental rental;
        private final Units units;
    }
}
