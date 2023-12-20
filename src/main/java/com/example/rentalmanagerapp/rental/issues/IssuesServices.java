package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.units.Units;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IssuesServices {

    private final IssuesRepository repository;

    public List<Issues> checkForIssue(Long userId){
        return repository.checkForIssue(userId)
                .orElseThrow(
                        ()->new IllegalStateException("No Issues Found"));
    }

    public String createIssue(
            Issues.createRequest issuePayload){
        if(checkForIssue(
                issuePayload.getUser().getId()).size() > 5){
            throw  new IllegalStateException(
                    "You have too many request open"
            );
        }

        Issues newIssue = new Issues(
                issuePayload.getUser(),
                issuePayload.getUser().getUsersUnit(),
                issuePayload.getUser().getUsersUnit().getUnitNumber(),
                issuePayload.getPriority(),
                issuePayload.getIssueBody(),
                issuePayload.getUser().getRentalAddress()
        );

        repository.save(newIssue);

        return "";
    }

    public List<Issues> getRentalIssues(String rentalAddress){
        List<Issues> returnList = new ArrayList<>();

        List<Issues> rentalsIssues = repository
                .getRentalsIssuesByAddress(rentalAddress)
                .orElseThrow(()-> new IllegalStateException("No Issues exist under than rental"));


        rentalsIssues.forEach(
                issues ->{
                    Issues newListItem = new Issues(
                        issues.getCreatedBy(),
                        issues.getUnitNumber(),
                        issues.getIssuePriority(),
                        issues.getIssueBody(),
                        issues.getIssueStatus(),
                        issues.getAdminNotes()
                );

                returnList.add(newListItem);
                }
        );

        return returnList;
    }

}
