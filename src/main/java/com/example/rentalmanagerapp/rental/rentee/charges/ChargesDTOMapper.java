package com.example.rentalmanagerapp.rental.rentee.charges;

import com.example.rentalmanagerapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class ChargesDTOMapper implements Function<Charges, ChargesDTO> {

    private final UserDTOMapper userDTOMapper;

    @Override
    public ChargesDTO apply(Charges charge){
        return new ChargesDTO(
                userDTOMapper.apply(
                       charge.getUser()
               ),
                charge.getReason(),
                userDTOMapper.apply(
                       charge.getCreatedBy()
               ),
                charge.isHasPaid(),
                charge.getAmountOwed(),
                charge.getAmountPaid(),
                charge.getPaidAt(),
                charge.getDueBy(),
                charge.getChargeId(),
                charge.getTransactionId()
        );
    }
}
