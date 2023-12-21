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

import com.example.rentalmanagerapp.rental.issues.enums.IssueStatus;
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
        return issuesServices.createIssue(issuesPayload);
    }

    @DeleteMapping("/delete")
    public String deleteIssue(@RequestBody Long issueId){
        return issuesServices.deleteIssue(issueId);
    }

    @PatchMapping("/updateStatus")
    public String updateStatus(@RequestBody Issues.updateStatus updateStatus){
        return issuesServices.updateStatus(updateStatus);
    }

    @GetMapping("/manger/getRentalIssues")
    public List<Issues> getRentalIssuesAsManager(String rentalAddress){
        return issuesServices.getRentalIssues(rentalAddress);
    }

}
