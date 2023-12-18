package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.issues.enums.IssuePriority;
import com.example.rentalmanagerapp.rental.issues.enums.IssueStatus;
import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@Table(name = "issues")
@Getter
@Setter
public class Issues {
    @SequenceGenerator(
            name = "rentalSequence",
            sequenceName = "rentalSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "rentalSequence"
    )


    @Id
    private Long id;

    @OneToOne
    private User createdBy;

    @OneToOne
    private User seenBy = null;

    @OneToOne
    private Units unit;

    private IssuePriority issuePriority;

    private IssueStatus issueStatus;

    private String adminNotes = " ";

    private String issueBody;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Issues(){

    }

    public Issues(User createdBy, Units unit, IssuePriority issuePriority, IssueStatus issueStatus, String issueBody) {
        this.createdBy = createdBy;
        this.unit = unit;
        this.issuePriority = issuePriority;
        this.issueStatus = issueStatus;
        this.issueBody = issueBody;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Setter
    @Getter
    public static class createRequest {
        private final User user;
        private final String issueBody;
        private final IssuePriority priority;
    }

}
