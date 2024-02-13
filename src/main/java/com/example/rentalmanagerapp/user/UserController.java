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


    @GetMapping("/register/checkEmail")
    public boolean checkEmail(@RequestParam("email") String email){
        return userService.checkEmail(email);
    }

    @GetMapping("/register/checkUsername")
    public boolean checkUsername(@RequestParam("username") String username){
        return userService.checkUsername(username);
    }

    @DeleteMapping("/delete")
    public String deleteUser(
            Principal user){
        //Todo validate jwt , add delete service
        return userService.deleteUser(user);
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAllUsers(){

        return userService.getAllUsers();
    }

    @PutMapping("/update")
    public String updateUser(
            @RequestBody User user){
        //Todo validate jwt before updating
        return userService.updateUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestBody User.UserLoginRequest loginPayload){
        return userService.userLogin(loginPayload);
    }
}
