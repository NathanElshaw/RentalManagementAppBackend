package com.example.rentalmanagerapp.Rental.Units;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.RentalCodes.UnitCodes;
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
@Table(name = "units")
public class Units {
        @SequenceGenerator(
                name = "unitsSequence",
                sequenceName = "unitsSequence",
                allocationSize = 1
        )

        @GeneratedValue(
                strategy = SEQUENCE,
                generator = "unitsSequence"
        )
        @Id
        private Long id;

        @ManyToOne
        @JoinColumn(
                nullable = false,
                name = "parent_unit_id"
        )
        private Rental parentUnitId;
        @OneToOne
        private UnitCodes unitCode;
        @OneToOne
        private User renter;
        private String unitAddress;
        private int beds;
        private double baths;
        private int unitNumber;
        private Boolean hasPets;
        private double rentAmount;
        private double rentDue;
        private double rentPaid;
        private LocalDate leaseStart;
        private LocalDate rentDueDate;
        private LocalDate leaseEnd;

        public Units(){

        }

        //Create Units Constructor
        public Units(int unitNumber,
                     int beds,
                     double baths,
                     String unitAddress,
                     boolean hasPets,
                     double rentAmount,
                     String rentDueDate,
                     String leaseStart,
                     String leaseEnd,
                     Rental parentUnitId) {
                this.unitNumber = unitNumber;
                this.beds = beds;
                this.baths = baths;
                this.unitAddress = unitAddress;
                this.hasPets = hasPets;
                this.rentAmount = rentAmount;
                this.rentDueDate = LocalDate.parse(rentDueDate);
                this.leaseStart = LocalDate.parse(leaseStart);
                this.leaseEnd = LocalDate.parse(leaseEnd);
                this.parentUnitId = parentUnitId;
        }

        //Return unit constructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class ReturnGetUnitsRequest {
                private String unitAddress;
                private int beds;
                private double baths;
                private int unitNumber;
                private Boolean hasPets;
                private double rentAmount;
                private double rentDue;
                private double rentPaid;
                private LocalDate leaseStart;
                private LocalDate rentDueDate;
                private LocalDate leaseEnd;

                public ReturnGetUnitsRequest(){}
        }
}
