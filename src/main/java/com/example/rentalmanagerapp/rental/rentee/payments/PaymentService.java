package com.example.rentalmanagerapp.rental.rentee.payments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    //Todo:
    //Call this api after payment confirmed
    //get how much was paid
    //then get unit via user id
    // add payment amount to unit and subtract amount owed from payment
    // add amount paid to rental total income
}
