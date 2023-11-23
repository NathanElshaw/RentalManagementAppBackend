package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
        private Long parentUnitId;
        private Long unitNumber;
        @ManyToOne
        private User Renter;
        private Long rentAmount;
        private Long rentDue;
        private Long rentPaid;
        private LocalDate rentDueDate;
        private LocalDate leaseEnd;

        public Units(){

        }


}
