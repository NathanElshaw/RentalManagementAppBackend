package com.example.rentalmanagerapp.rental.issues;


//Todo
/*
*Create issue
* Update Issue
* delete Issue
* getAllIssues from rental
* getIssues from a user
* getIssues by priority
* getIssue by status
* getIssues from rental by status
* getIssues for manager of rental
*/

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/issues")
public class IssuesController {

    private final IssuesServices issuesServices;

    @PostMapping("/create")
    public String createIssue(@RequestBody Issues.createRequest issuesPayload){
        return issuesServices.createIssue(issuesPayload);
    }

}
