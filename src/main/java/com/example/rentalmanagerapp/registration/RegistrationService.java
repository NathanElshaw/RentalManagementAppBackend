package com.example.rentalmanagerapp.registration;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    public String createToken(){
        //Todo: add code creator
        return "";
    }

    public String sendToken(
            String email){
        //todo send token to email to validate account
        return "";
    }

    public void confirmToken(
            String token){
        User reqUser = userRepository
                .confirmWithToken(token)
                .orElseThrow(()->
                        new BadRequestException("Invalid code"));

        reqUser.setActive(true);

        userRepository.save(reqUser);
    }

}
