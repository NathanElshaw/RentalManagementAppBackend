package com.example.rentalmanagerapp.rental.issues;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                issuePayload.getPriority(),
                issuePayload.getIssueBody()
        );

        repository.save(newIssue);

        return "";
    }

}
