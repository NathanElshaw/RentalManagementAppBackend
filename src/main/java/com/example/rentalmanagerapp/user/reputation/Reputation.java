package com.example.rentalmanagerapp.user.reputation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Reputation {
    private int repScore;

    private Review[] usersReviews;

    private LocalDateTime updatedAt;

    public Reputation () {

    }
}
