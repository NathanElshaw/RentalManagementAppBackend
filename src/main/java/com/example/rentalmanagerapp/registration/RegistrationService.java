package com.example.rentalmanagerapp.registration;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class RegistrationService implements EmailSender{

    private final UserRepository userRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender mailSender;

    public String createToken(){
        Random random = new Random();
        //Todo: add code creator
        String[] validChars = {
                "a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3",
                "4", "5", "6", "7", "8", "9", "0"
        };

        StringBuilder code = new StringBuilder();

        for(int i = 0; i < 6; i++){
            code
                    .append(
                            validChars[ random.ints(0, 35)
                                    .findFirst()
                                    .getAsInt()]);
        }
        return code.toString();
    }

    @Override
    @Async
    public void send(
            String email,
            String code){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText("Your code is " + code, true);
            helper.setTo(email);
            helper.setSubject("Confirm your email");
            helper.setFrom("HelloFromNate@Gmail.com");
            mailSender.send(mimeMessage);
        }catch(MessagingException e){
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Email failed to send");
        }
    }

    public void confirmToken(
            String token){
        User reqUser = userRepository
                .confirmWithToken(token)
                .orElseThrow(()->
                        new BadRequestException("Invalid code"));

        reqUser.setActive(true);

        userRepository.save(reqUser);
    }

}
