package com.example.rentalmanagerapp.rental.rentee.charges;

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

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@Table(name="charges")
@Getter
@Setter
public class Charges {
    @SequenceGenerator(
            name = "chargesSequence",
            sequenceName = "chargesSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "chargesSequence"
    )
    @Id
    private Long id;

    @OneToOne
    private User user;

    private String reason;

    @OneToOne
    private User createdBy;

    private boolean hasPaid;

    private double amountOwed;

    private double amountPaid;

    private  double chargeAmount;

    private LocalDateTime paidAt;

    private LocalDate dueBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String chargeId;

    private String transactionId = null;


    public Charges (){

    }

    public Charges(
            User user,
            String reason,
            User createdBy,
            boolean hasPaid,
            double amountOwed,
            double amountPaid,
            double chargeAmount,
            String chargeId) {
        this.user = user;
        this.reason = reason;
        this.createdBy = createdBy;
        this.hasPaid = hasPaid;
        this.amountOwed = amountOwed;
        this.amountPaid = amountPaid;
        this.chargeAmount = chargeAmount;
        this.chargeId = chargeId;
    }
}
