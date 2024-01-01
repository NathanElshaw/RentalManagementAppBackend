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
    private User createdBy = null;

    @OneToOne
    private User seenBy = null;

    @OneToOne
    private Units unit = null;

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

    public Issues(String issueBody,
                  IssuePriority issuePriority,
                  int unitNumber){
        this.issueBody = issueBody;
        this.issuePriority = issuePriority;
        this.unitNumber = unitNumber;
    }

    public Issues(User user,
                  String issueBody,
                  IssuePriority issuePriority){
        this.createdBy = user;
        this.issueBody = issueBody;
        this.issuePriority = issuePriority;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(User seenBy) {
        this.seenBy = seenBy;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public IssuePriority getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(IssuePriority issuePriority) {
        this.issuePriority = issuePriority;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public String getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(String issueBody) {
        this.issueBody = issueBody;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
