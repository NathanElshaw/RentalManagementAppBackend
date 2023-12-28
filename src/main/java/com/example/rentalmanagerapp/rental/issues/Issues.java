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

import static com.example.rentalmanagerapp.rental.issues.enums.IssueStatus.Sent;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@Table(name = "issues")
@Getter
@Setter
public class Issues {
    @SequenceGenerator(
            name = "issuesSequence",
            sequenceName = "issuesSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "issuesSequence"
    )


    @Id
    private Long id;

    @OneToOne
    private User createdBy;

    @OneToOne
    private User seenBy = null;

    @OneToOne
    private Units unit;

    private int unitNumber;

    private String unitAddress;

    private IssuePriority issuePriority;

    private IssueStatus issueStatus = Sent;

    private String adminNotes = " ";

    private String issueBody;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Issues() {

    }

    public Issues(User createdBy,
                  Units unit,
                  int unitNumber,
                  IssuePriority issuePriority,
                  String issueBody,
                  String unitAddress) {
        this.createdBy = createdBy;
        this.unit = unit;
        this.unitNumber = unitNumber;
        this.issuePriority = issuePriority;
        this.issueBody = issueBody;
        this.unitAddress = unitAddress;
    }

    public Issues(User createdBy,
                  int unitNumber,
                  IssuePriority issuePriority,
                  String issueBody,
                  IssueStatus issueStatus,
                  String adminNotes
                  ) {
        this.createdBy = createdBy;
        this.unitNumber = unitNumber;
        this.issuePriority = issuePriority;
        this.issueStatus = issueStatus;
        this.issueBody = issueBody;
        this.adminNotes = adminNotes;

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

    @AllArgsConstructor
    @EqualsAndHashCode
    @Setter
    @Getter
    public static class updateStatus {
        private final Long id;
        private final IssueStatus issueStatus;
    }
    @AllArgsConstructor
    @EqualsAndHashCode
    @Setter
    @Getter
    public static class updateSeenBy {
        private final Long issueId;
        private final User userId;
    }

}
