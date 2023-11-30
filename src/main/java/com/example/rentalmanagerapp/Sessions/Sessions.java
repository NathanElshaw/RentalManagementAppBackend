package com.example.rentalmanagerapp.Sessions;

import com.example.rentalmanagerapp.Sessions.JWT.JWT;
import com.example.rentalmanagerapp.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
public class Sessions {
    @Id
    private long id;
    @OneToOne
    private JWT jwt;
    @OneToOne
    private User user;
    private int sessionAmounts;
    private LocalDate lastSession;
    private LocalDateTime startOfSession;

    public Sessions () {

    }

}
