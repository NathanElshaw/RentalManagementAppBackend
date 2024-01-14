package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@Setter
@Getter
public class Sessions {
    @SequenceGenerator(
            name = "sessionSequence",
            sequenceName = "sessionSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "sessionSequence"
    )
    @Id
    private Long id;

    @OneToOne
    private User user;

    private int sessionAmount;

    private boolean isActive;

    private LocalDateTime lastSessionStart;

    private LocalDateTime startOfSession;

    public Sessions () {

    }

    public Sessions(User user, int sessionAmount, LocalDateTime lastSessionStart, LocalDateTime startOfSession) {
        this.user = user;
        this.sessionAmount = sessionAmount;
        this.lastSessionStart = lastSessionStart;
        this.startOfSession = startOfSession;
    }
}
