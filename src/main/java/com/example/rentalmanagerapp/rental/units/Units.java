package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

        @ManyToOne
        @JoinColumn(
                nullable = true,
                name = "unit_code_id"
        )
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

        @OneToOne
        private User createdBy;

        private LocalDateTime createdAt = LocalDateTime.now();

        private LocalDateTime updatedAt = LocalDateTime.now();

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

        public Units(
                String unitAddress,
                int unitNumber,
                Rental parentUnitId
        ){
                this.unitAddress = unitAddress;
                this.unitNumber = unitNumber;
                this.parentUnitId = parentUnitId;
        }

        public Units(
                long id,
                User user,
                String unitAddress,
                int unitNumber,
                Rental parentUnitId
        ){
                this.id = id;
                this.renter = user;
                this.unitAddress = unitAddress;
                this.unitNumber = unitNumber;
                this.parentUnitId = parentUnitId;
        }
        public Units(
                User user,
                String unitAddress,
                int unitNumber,
                Rental parentUnitId
        ){
                this.renter = user;
                this.unitAddress = unitAddress;
                this.unitNumber = unitNumber;
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

        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class GetAllUnitsWithDetails{
                private Long id;

                private Rental parentUnitId;

                private UnitCodes unitCode;

                private User.UnitUserRequest renter;

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
        }
}
