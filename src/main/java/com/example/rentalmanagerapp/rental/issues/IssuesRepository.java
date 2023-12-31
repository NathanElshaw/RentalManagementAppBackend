package com.example.rentalmanagerapp.rental.issues;


import com.example.rentalmanagerapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IssuesRepository  extends JpaRepository<Issues, Long> {

    @Query("select case when count(i) > 0 then " +
            "true else false end " +
            "from Issues i " +
            "where i = ?1 ")
    Boolean assertIssueExists(Issues issues);

    @Query("select i from Issues i " +
            "where i.createdBy = ?1 ")
    List<Issues> checkForIssue (User userId);

    @Query("select count(i) from Issues i " +
            "where i.createdBy = ?1")
    int getIssueAmount(User user);

    @Query("select i from Issues i " +
            "where i.unitAddress = ?1 ")
    List<Issues> getRentalsIssuesByAddress(String rentalAddress);

    @Transactional
    @Modifying
    @Query(" update Issues i " +
            "set i = ?2 " +
            "where i.id = ?1 ")
    void update(Long id, Issues issue);

}
