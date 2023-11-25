package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodes;
import com.example.rentalmanagerapp.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "subUnits")
public class Units {
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
        @OneToOne
        private Rental parentUnitId = null;
        @ManyToOne
        private User Renters = null;
        @OneToOne
        private UnitCodes unitCode;

        private Long unitNumber;
        private Boolean hasPets;
        private Long rentAmount;
        private Long rentDue;
        private Long rentPaid;
        private LocalDate rentDueDate;
        private LocalDate leaseEnd;

        public Units(){

        }

        public Units(Long unitNumber, Long rentAmount) {
                this.unitNumber = unitNumber;
                this.rentAmount = rentAmount;
        }
}
