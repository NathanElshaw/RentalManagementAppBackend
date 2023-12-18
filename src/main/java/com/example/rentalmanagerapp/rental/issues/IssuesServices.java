package com.example.rentalmanagerapp.rental.issues;

import org.springframework.stereotype.Service;

@Service
public class IssuesServices {

    public String createIssue(
            Issues.createRequest issuePayload){
        Issues newIssue = new Issues(
                issuePayload.getUser(),
                issuePayload.getUser().getU
                issuePayload.getIssueBody(),
                issuePayload.getPriority()
        )
        return "";
    }

}
