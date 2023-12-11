package com.example.rentalmanagerapp.rental.rentee.payments;

import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

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

    private PaymentTypes paymentMethod = null;

    private LocalDateTime paymentTime;

    private String stripePaymentId = null;

    private Month paymentMonth;


    public Payment(){

    }

    public Payment(
            User user,
            double amountPaid,
            PaymentTypes paymentMethod,
            LocalDateTime paymentTime,
            String stripePaymentId,
            Month paymentMonth) {
        this.user = user;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.stripePaymentId = stripePaymentId;
        this.paymentMonth = paymentMonth;
    }

    public Payment(
            User user,
            double amountPaid,
            PaymentTypes paymentMethod,
            LocalDateTime paymentTime,
            Month paymentMonth) {
        this.user = user;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.paymentMonth = paymentMonth;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class UserPaymentRequest {
        private final Long userId;

        private final double paymentAmount;

        private final PaymentTypes paymentMethod;

        private final String stripeTransactionId;

        private final int paymentMonth;

        private final String chargeId;
    }

}
