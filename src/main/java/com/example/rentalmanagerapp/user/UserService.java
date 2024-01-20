package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String createUser(User user){
            boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
            boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();

            if(userExists){
               throw new IllegalStateException("User already exists");
            }

            if(emailExists){
                throw new IllegalStateException("Email already exists");
            }

            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);

            userRepository.save(user);

            return UUID.randomUUID().toString();
    }

    public String updateUser(User user){

        boolean userExists =
                userRepository.assertUserExists(user.getId());

        if(!userExists){
            throw new IllegalStateException("Cannot update");
        }

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.updateUser(user.getId(), user);

        return "Success";
    }

    public String deleteUser(User user){

        boolean userExists = userRepository.assertUserExists(user.getId());

        if(!userExists){
            throw new IllegalStateException("User does not exist");
        }

        userRepository.delete(user);

        return "Deleted user";
    }

    public ResponseEntity<String> userLogin(
            User.UserLoginRequest userLoginPayload){
        User targetUser = userRepository.findByUsername(
                userLoginPayload.getUsername()).orElseThrow(
                ()->new IllegalStateException("Invalid username or password")
        );

        if(bCryptPasswordEncoder.matches(
                userLoginPayload.getPassword(),
                targetUser.getPassword())){

            HttpHeaders authHeader = new HttpHeaders();

            authHeader.set("Jwt", "jwt");

            return new ResponseEntity<String>("Good", authHeader, HttpStatus.CREATED);
        }else{
            throw new IllegalStateException("Invalid username or password");
        }

        //TODO create jwt on password match then create a session in the sessions table.
    }
}
