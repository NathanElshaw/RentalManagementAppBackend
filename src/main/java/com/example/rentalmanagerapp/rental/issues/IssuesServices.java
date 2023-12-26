package com.example.rentalmanagerapp.rental.issues;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IssuesServices {

    private final IssuesRepository repository;

    private IllegalStateException issueNotFound (){
        return new IllegalStateException("Issue not found");
    }

    private IllegalStateException throwError(String errorInfo){
        return new IllegalStateException(errorInfo);
    }

    public List<Issues> checkForIssues(Long userId){
        return repository.checkForIssue(userId)
                .orElseThrow(
                        this::issueNotFound);
    }

    public String createIssue(
            Issues.createRequest issuePayload){
        if(checkForIssues(
                issuePayload.getUser().getId()).size() > 5){
            throw throwError("You have too many request open");
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
                .orElseThrow(()-> throwError(
                        "No Issues exist under than rental"));


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

    public String updateSeenBy(Issues.updateSeenBy updateSeenBy){
        repository
                .findById(updateSeenBy.getIssueId())
                .orElseThrow(this::issueNotFound);

        repository.updateSeenBy(
                updateSeenBy.getIssueId(),
                updateSeenBy.getUserId()
        );

        return "Success";
    }

    public String updateStatus(Issues.updateStatus updateStatus){
        repository
                .findById(updateStatus.getId())
                .orElseThrow(this::issueNotFound);

        repository.updateStatus(
                updateStatus.getId(),
                updateStatus.getIssueStatus());

        return "Success";

    }

    public String deleteIssue(Long issueId){
        Issues targetIssue = repository.findById(issueId)
                .orElseThrow(this::issueNotFound);

        repository.delete(targetIssue);

        return "Successfully Deleted";
    }

}
