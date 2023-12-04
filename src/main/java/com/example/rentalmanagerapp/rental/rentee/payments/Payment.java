package com.example.rentalmanagerapp.rental.rentee.payments;

import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @SequenceGenerator(
            name = "paymentSequence",
            sequenceName = "paymentSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "paymentSequence"
    )
    @Id
    private Long id;

    @OneToOne
    private User user;

    private double amountPaid;

    private String method; //Probably stripe for now

    private LocalDateTime paymentTime;


    public Payment(){

    }

}
