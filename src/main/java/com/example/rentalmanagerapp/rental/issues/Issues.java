package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.issues.enums.IssuePriority;
import com.example.rentalmanagerapp.rental.issues.enums.IssueStatus;
import com.example.rentalmanagerapp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Table(name = "issues")
@Getter
@Setter
public class Issues {

    @Id
    private Long id;

    @OneToOne
    private User createdBy;

    private IssuePriority issuePriority;

    private IssueStatus issueStatus;

    @OneToOne
    private User seenBy;

    private String issueBody;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
