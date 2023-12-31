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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/issues")
public class IssuesController {

    private final IssuesServices issuesServices;

    @PostMapping("/create")
    public String createIssue(@RequestBody Issues.createRequest issuesPayload){
        int issueAmount = issuesServices.checkForIssues(issuesPayload.getUser());
        if(issueAmount > 5){
            throw new IllegalStateException("Too many issues active");
        }
        //create Issue here
        Issues issue = new Issues();
        return issuesServices.createIssue(issue);
    }

    @DeleteMapping("/delete")
    public String deleteIssue(@RequestBody Issues issue){
        return issuesServices.deleteIssue(issue);
    }

    @PatchMapping("/update")
    public String updateStatus(@RequestBody Issues issues){
        return issuesServices.updateIssue(issues);
    }

    @GetMapping("/manger/getRentalIssues")
    public List<Issues> getRentalIssuesAsManager(String rentalAddress){
        return issuesServices.getRentalIssues(rentalAddress);
    }

    @GetMapping("/getAll")
    public List<Issues> getAllIssues(){
        return issuesServices.getAllIssues();
    }

}
