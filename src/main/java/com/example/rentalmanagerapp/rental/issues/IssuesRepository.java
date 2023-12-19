package com.example.rentalmanagerapp.rental.issues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuesRepository  extends JpaRepository<Issues, Long> {
    @Query("select i from Issues i where i.createdBy = ?1 ")
    Optional<List<Issues>> checkForIssue (Long userId);


}
