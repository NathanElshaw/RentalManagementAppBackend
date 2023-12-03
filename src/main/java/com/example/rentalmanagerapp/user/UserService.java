package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createUser(UserSignUpRequest user){
        try {
            boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
            boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();

            if(userExists){
               throw new IllegalStateException("User already Exists");
            }

            if(emailExists){
                throw new IllegalStateException("Email already exists");
            }

            LocalDate convertedBirthDate = LocalDate.parse(user.getBirthDate());
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

            User creatingUser = new User(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getFirstName() + " " + user.getLastName(),
                    convertedBirthDate,
                    user.getAddress(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getUsername(),
                    encodedPassword
            );
            userRepository.save(creatingUser);

            return UUID.randomUUID().toString();
        }catch(Exception e){
            System.out.println(e.toString());
            throw new IllegalStateException(e);
        }
    }

    public String updateUser(User user){
        if(user == null){
            throw new IllegalStateException("Cannot update");
        }
        return "";
    }

    public String userLogin(
            User.UserLoginRequest userLoginPayload){
        User targetUser = userRepository.findByUsernameLogin(
                userLoginPayload.getUsername()).orElseThrow(
                ()->new IllegalStateException("Invalid username or password")
        );

        String encodedPassword = bCryptPasswordEncoder.encode(
                userLoginPayload.getPassword());

        if(bCryptPasswordEncoder.matches(
                userLoginPayload.getPassword(),
                targetUser.getPassword())){
            return "passwords match";
        }

        return "Invalid username or password 2";
        //TODO create jwt on password match then create a session in the sessions table.
    }
}
