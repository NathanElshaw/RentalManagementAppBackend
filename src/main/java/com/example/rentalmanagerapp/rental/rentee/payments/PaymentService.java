package com.example.rentalmanagerapp.rental.rentee.payments;

import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;

    private final UnitsRepository unitsRepository;

    public String createPayment(
        Payment.UserPaymentRequest paymentPayload
    ){
        User user = userRepository.findById(
                paymentPayload.getUserId()).orElseThrow(
                ()-> new IllegalStateException("User not found")
        );

        Units unit = unitsRepository.findByUser(
                user).orElseThrow(
                ()->new IllegalStateException("User has no unit")
        );

        //Todo: Validate Stripe payment here and check for dupes

        Payment newPayment = new Payment(
                user,
                paymentPayload.getPaymentAmount(),
                LocalDateTime.now(),
                paymentPayload.getStripeTransactionId()
        );

        unitsRepository.userPayment(
                user,
                paymentPayload.getPaymentAmount(),
                unit.getRentDue() -
                        paymentPayload.getPaymentAmount());

        paymentRepository.save(newPayment);

        return "Successfully paid";
    }

    //Todo:
    //Call this api after payment confirmed
    //get how much was paid
    //then get unit via user id
    // add payment amount to unit and subtract amount owed from payment
    // add amount paid to rental total income
}
