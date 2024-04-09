package com.example.rentalmanagerapp.user.reputation;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;


@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "UserReviews")
public class Review {

    @SequenceGenerator(
            name = "reviewSequence",
            sequenceName = "reviewSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "reviewSequence"
    )

    @Id
    private Long id;

    @ManyToOne
    private User reviewer;

    private int reviewScore;

    private String reviewBody;

    private LocalDateTime reviewDateTime;

    private LocalDateTime updatedAt;

    public Review() {

    }

}
