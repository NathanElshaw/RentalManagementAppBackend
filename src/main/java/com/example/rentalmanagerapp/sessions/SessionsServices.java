package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SessionsServices {

    private final SessionsRepository sessionsRepository;

    private final UserRepository userRepository;

    public String createSession(
            Long userId){
         if(sessionsRepository.findByUserId(
                 userId).isEmpty()){
             User targetUser = userRepository.findById(userId)
                     .orElseThrow(
                     ()->new IllegalStateException("User not found")
             );

             Sessions newSession = new Sessions(
                     targetUser,
                     1,
                     LocalDateTime.now(),
                     LocalDateTime.now()
             );

             sessionsRepository.save(newSession);

             return "Session created for " + targetUser.getFirstName() + " " + targetUser.getLastName();
         }else{
             return updateSession(userId);
         }
    }

    public String updateSession(
            Long userId){
        User targetUser = userRepository.findById(
                userId).orElseThrow(
                ()->new IllegalStateException("User not found")
        );

        Sessions targetSession = sessionsRepository.findByUserId(
                userId).orElseThrow(
                ()->new IllegalStateException("Session not found")
        );

        sessionsRepository.updateUserSession(
                targetUser,
                targetSession.getSessionAmount() + 1,
                targetSession.getStartOfSession(),
                LocalDateTime.now());

        return "Session created for 2" +
                targetUser.getFirstName() +
                " " +
                targetUser.getLastName();

    }

}
