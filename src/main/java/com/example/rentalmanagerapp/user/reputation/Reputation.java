package com.example.rentalmanagerapp.user.reputation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Reputation")
public class Reputation {

    @SequenceGenerator(
            name = "userScoreSequence",
            sequenceName = "userScoreSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "userScoreSequence"
    )

    @Id
    private Long id;

    private int repScore;

    @ManyToOne
    private Review usersReviews;

    private LocalDateTime updatedAt;

    public Reputation () {

    }
}
