package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtService jwtService;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("Username not found"));
            }
        };
    }

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

    public List<User> getAllUsers(){

        //modify with dto

        return userRepository.getAllUsers();
    }

    public String deleteUser(Principal user){

        User reqUser = (User) ((UsernamePasswordAuthenticationToken) user).getPrincipal();


        userRepository.delete(reqUser);

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

            authHeader.set(HttpHeaders.SET_COOKIE, jwtService
                    .generateToken(userDetailsService()
                            .loadUserByUsername(userLoginPayload.getUsername())));

            return new ResponseEntity<>("Success", authHeader, HttpStatus.CREATED);
        }else{
            throw new IllegalStateException("Invalid username or password");
        }
    }
}
