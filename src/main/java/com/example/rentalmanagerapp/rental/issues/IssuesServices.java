package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IssuesServices {

    private final IssuesRepository repository;

    private final UserRepository userRepository;

    private Boolean validateUser(String email){
       return userRepository.findByEmail(email).isPresent();
    }

    private RuntimeException issueNotFound (){
        return new IllegalStateException("Issue not found");
    }

    private  RuntimeException newError(
            String errorInfo){
        return new IllegalStateException(errorInfo);
    }

    public List<Issues> getAllIssues(){
        return repository.findAll();
    }


    public List<Issues> checkForIssues(User user){

        if(!validateUser(user.getEmail())){
            throw newError("User not found");
        }

        return repository.checkForIssue(user)
                .orElseThrow(
                        this::issueNotFound);
    }

    public String createIssue(
            Issues issue){

        repository.save(issue);

        return "";
    }

    public List<Issues> getRentalIssues(String rentalAddress){
        List<Issues> returnList = new ArrayList<>();

        List<Issues> rentalsIssues = repository
                .getRentalsIssuesByAddress(rentalAddress)
                .orElseThrow(()-> newError("No Issues exist under than rental"));


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
        Issues targetIssue = repository
                .findById(issueId)
                .orElseThrow(this::issueNotFound);

        repository.delete(targetIssue);

        return "Successfully Deleted";
    }

}
