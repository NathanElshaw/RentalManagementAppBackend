package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.registration.RegistrationService;
import com.example.rentalmanagerapp.rental.unitcode.UnitCodesService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UnitCodesService unitCodesService;

    private final RegistrationService registrationService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtService jwtService;

    private final UserDTOMapper userDTOMapper;

    public UserDetailsService userDetailsService(){
        return username -> userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username not found"));
    }

    public boolean checkUsername(String username){
        return userRepository.assertUsernameExists(username);
    }

    public boolean checkEmail(String email){
        return userRepository.assertEmailExists(email);
    }

    public String createUser(User user, String... joinCode){

        if(userRepository
                .findByUsername(user
                        .getUsername()).isPresent()){
            throw new IllegalStateException("User already exists");
        }

        if(userRepository
                .findByEmail(user
                        .getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }

            String code = registrationService.createToken();


            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);
            user.setFullName(
                    user.getFirstName() +
                            " " +
                            user.getLastName());
            user.setConfirmCode(code);

            registrationService.send(
                    user.getEmail(),
                    code
            );

            userRepository.save(user);

            if(joinCode[0] != null && unitCodesService.checkCode(joinCode[0])){
                User newUser = userRepository.findByEmail(
                        user.getEmail()
                ).orElseThrow();

                unitCodesService
                        .createUserJoinUnit(newUser, joinCode[0]);
            }
                return "Done";
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

    public List<UserDTO> getAllUsers(){

        List<UserDTO> dtoList = new ArrayList<>();

        List<User> userList = userRepository.getAllUsers();

        userList.forEach(user -> {
            dtoList.add(userDTOMapper.apply(user));
        });

        return dtoList;
    }

    public String deleteUser(Principal user){

        User reqUser = (User)
                ((UsernamePasswordAuthenticationToken) user)
                        .getPrincipal();

        userRepository.delete(reqUser);

        return "Deleted user";
    }

    public UserDTO getUser(Principal user){
        return userDTOMapper.apply(
                (User) ((UsernamePasswordAuthenticationToken) user)
                        .getPrincipal()
        );
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
