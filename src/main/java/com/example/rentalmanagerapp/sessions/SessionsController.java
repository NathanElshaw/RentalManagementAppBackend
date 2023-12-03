package com.example.rentalmanagerapp.sessions;

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
            @RequestBody Sessions.createSessionRequest requestPayload){
        return sessionsServices.createSession(requestPayload.getUserId());
    }

    @PatchMapping("/update")
    public String updateSession(
            @RequestBody Sessions.createSessionRequest requestPayload){
        return sessionsServices.updateSession(requestPayload.getUserId());
    }
}
