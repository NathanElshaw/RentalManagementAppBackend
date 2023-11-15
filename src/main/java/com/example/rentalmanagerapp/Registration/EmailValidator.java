package com.example.rentalmanagerapp.Registration;

import com.example.rentalmanagerapp.User.User;
import com.example.rentalmanagerapp.User.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class EmailValidator {

   private final UserRepository userRepository;

    // Todo regex for email validation
    public boolean validate(String email){
        return true;
    }

    public boolean validateAgainstDb(String email) {
        //Todo check db for existing acc with email if none return true
        Optional<User> isExisting = userRepository.findByEmail(email);
        return isExisting.isEmpty();
    }

}
