package com.example.rentalmanagerapp.User;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public String createUser(@RequestBody UserSignUpRequest user){
        return userService.createUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("User") String username){
        //Todo validate jwt , add delete service
        return "";
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user){
        //Todo validate jwt before updating
        return userService.updateUser(user);
    }
}
