package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.issues.enums.IssueStatus;
import com.example.rentalmanagerapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuesRepository  extends JpaRepository<Issues, Long> {
    @Query("select i from Issues i " +
            "where i.createdBy = ?1 ")
    Optional<List<Issues>> checkForIssue (Long userId);

    @Query("select i from Issues i " +
            "where i.unitAddress = ?1 ")
    Optional<List<Issues>> getRentalsIssuesByAddress(String rentalAddress);

    @Transactional
    @Modifying
    @Query("update Issues i " +
            "set i.issueStatus = ?2 " +
            "where i.id = ?1 ")
    void updateStatus(Long issueId, IssueStatus newStatus);

    @Transactional
    @Modifying
    @Query(" update Issues i " +
            "set i.seenBy = ?2 " +
            "where i.id = ?1 ")
    void updateSeenBy(Long id, User user);

}
