package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.unitcode.UnitCodesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UnitCodesService unitCodesService;

    private final UserService userService;

    @PostMapping("/register")
    public String createUser(
            @RequestBody User user,
            @RequestParam(value = "joinCode", required = false) String joinCode){
                    return userService.createUser(user, joinCode);
    }

    @PatchMapping("/join")
    public String joinUnit(
            @RequestParam(value = "code", required = false) String code,
            Principal user){
                return unitCodesService.joinUnit(code, user);
    }

    @GetMapping("/getUser")
    public UserDTO getUser(
            Principal user){
        return userService.getUser(user);
    }

    @GetMapping("/register/checkEmail")
    public boolean checkEmail(
            @RequestParam("email") String email){
        return userService.checkEmail(email);
    }

    @GetMapping("/register/checkUsername")
    public boolean checkUsername(
            @RequestParam("username") String username){
        return userService.checkUsername(username);
    }

    @DeleteMapping("/delete")
    public String deleteUser(
            Principal user){
        return userService.deleteUser(user);
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestBody User user,
            Principal reqUser) {
        return userService.updateUser(user, reqUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestBody User.UserLoginRequest loginPayload){
        return userService.userLogin(loginPayload);
    }
}
