package com.example.rentalmanagerapp.rental.rentee.payments;

import com.example.rentalmanagerapp.rental.Rental;
import com.example.rentalmanagerapp.rental.RentalRepository;
import com.example.rentalmanagerapp.rental.rentee.charges.Charges;
import com.example.rentalmanagerapp.rental.rentee.charges.ChargesRepository;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    private final UserRepository userRepository;

    private final UnitsRepository unitsRepository;

    private final RentalRepository rentalRepository;

    private final ChargesRepository chargesRepository;

    private IllegalStateException paymentNotFound(){
        return new IllegalStateException("Payment not found");
    }

    private IllegalStateException error(String s){
        return new IllegalStateException(s);
    }

    public String createPayment(
        Payment.UserPaymentRequest paymentPayload
    ){
        Payment newPayment;

        User user = userRepository.findById(
                paymentPayload.getUserId()).orElseThrow(
                ()-> error("User not found"));

        Units unit = unitsRepository
                .findByUser(user)
                .orElseThrow(()->error("User has no unit"));

        Rental rental = rentalRepository
                .findByRentalAddress(unit.getUnitAddress())
                .orElseThrow(()->error("Rental doesn't exist"));

        //Todo: Validate Stripe payment here and check for dupes

        if(paymentPayload.getPaymentMethod() != PaymentTypes.Stripe){
            newPayment = new Payment(
                    user,
                    paymentPayload.getPaymentAmount(),
                    paymentPayload.getPaymentMethod(),
                    LocalDateTime.now(),
                    Month.of(
                            paymentPayload.getPaymentMonth()
                    )
            );

        }else{
            newPayment = new Payment(
                    user,
                    paymentPayload.getPaymentAmount(),
                    paymentPayload.getPaymentMethod(),
                    LocalDateTime.now(),
                    paymentPayload.getStripeTransactionId(),
                    Month.of(
                            paymentPayload.getPaymentMonth()
                    )
            );
        }


        if(paymentPayload.getChargeId() != null){
            Charges targetCharge = chargesRepository
                    .findByChargeId(paymentPayload.getChargeId())
                    .orElseThrow(
                    ()->error("Charge not found"));

            double amountOwed = targetCharge.getAmountOwed() -
                    paymentPayload.getPaymentAmount();

            chargesRepository.makePayment(
                    targetCharge.getChargeId(),
                    paymentPayload.getPaymentAmount(),
                    amountOwed,
                    amountOwed >= 0,
                    LocalDateTime.now()
                    );
        }

        unitsRepository.userPayment(
                user,
                paymentPayload.getPaymentAmount(),
                unit.getRentDue() -
                        paymentPayload.getPaymentAmount());

        rentalRepository.updateRentalIncome(
                rental.getId(),
                rental.getTotalRentIncome() +
                paymentPayload.getPaymentAmount());

        repository.save(newPayment);

        return "Successfully paid";
    }

    //Todo:
    //Make sure if no money is needed prevent from making payments for this month.
    //Call this api after payment confirmed
    //get how much was paid
    //then get unit via user id
    // add payment amount to unit and subtract amount owed from payment
    // add amount paid to rental total income
}
