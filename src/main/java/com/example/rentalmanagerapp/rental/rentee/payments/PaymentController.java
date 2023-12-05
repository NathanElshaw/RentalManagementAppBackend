package com.example.rentalmanagerapp.rental.rentee.payments;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public String createPayment(
            @RequestBody Payment.UserPaymentRequest paymentPayload){
        return paymentService.createPayment(paymentPayload);
    }

}
