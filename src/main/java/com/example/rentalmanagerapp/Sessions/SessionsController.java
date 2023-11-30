package com.example.rentalmanagerapp.Sessions;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/session")
public class SessionsController {

    @PostMapping("/create")
    public String createSession(){
        return "";
    }
}
