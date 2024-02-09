package com.example.rentalmanagerapp.registration;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;

    public String createToken(){
        Random random = new Random();
        //Todo: add code creator
        String[] validChars = {
                "a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3",
                "4", "5", "6", "7", "8", "9", "0"
        };

        StringBuilder code = new StringBuilder();

        for(int i = 0; i < 6; i++){
            code
                    .append(
                            validChars[ random.ints(0, 35)
                                    .findFirst()
                                    .getAsInt()]);
        }
        return code.toString();
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
