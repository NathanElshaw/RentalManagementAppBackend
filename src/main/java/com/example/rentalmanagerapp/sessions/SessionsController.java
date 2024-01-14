package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/session")
public class SessionsController {

    private final SessionsServices sessionsServices;

    @PostMapping("/create")
    public String createSession(
            @RequestBody User user){
        return sessionsServices.createSession(user);
    }

    @PatchMapping("/update")
    public String updateSession(
            @RequestBody User user){
        return sessionsServices.updateSession(user);
    }

    @PatchMapping("/delete")
    public String deleteSession(
            @RequestBody User user
    ){
        return sessionsServices.deleteSession(user);
    }
}
