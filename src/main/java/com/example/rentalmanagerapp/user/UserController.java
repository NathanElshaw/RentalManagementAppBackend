package com.example.rentalmanagerapp.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public String createUser(
            @RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(
            Principal user){
        //Todo validate jwt , add delete service
        return userService.deleteUser(user);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
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
