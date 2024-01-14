package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
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
            User user){
         if(sessionsRepository.findByUserId(
                 user.getId()).isEmpty()){
             
             User targetUser = userRepository.findById(user.getId())
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
             return updateSession(user);
         }
    }

    public String updateSession(User user){

        boolean userExist = userRepository.assertUserExists(user.getId());

        if(!userExist){
            throw new IllegalStateException("User not found");
        }

        Sessions targetSession = sessionsRepository.findByUserId(
                user.getId()).orElseThrow(
                ()->new IllegalStateException("Session not found")
        );

        targetSession.setSessionAmount(targetSession
                .getSessionAmount() + 1);
        targetSession.setLastSessionStart(targetSession
                .getStartOfSession());
        targetSession.setStartOfSession(LocalDateTime.now());

        sessionsRepository.updateUserSession(
                targetSession.getId(),
                targetSession);

        return "Session created for 2" +
                user.getFirstName() +
                " " +
                user.getLastName();

    }

    public String deleteSession(User user){
        boolean userExist = userRepository.assertUserExists(user.getId());

        if(!userExist){
           throw new BadRequestException("User not found");
        }

        sessionsRepository.changeSessionStatus(
                user,
                false
        );

        return "Status changed";
    }

}
