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


    public int checkForIssues(User user){

        if(!validateUser(user.getEmail())){
            throw newError("User not found");
        }

        return repository.checkForIssue(user)
                .orElseThrow(
                        this::issueNotFound).size();
    }

    public String createIssue(
            Issues issue){

        repository.save(issue);

        return "Success";
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

    public String updateIssue(Issues issue){
        boolean doesExist = repository
                .findById(issue.getId())
                .isPresent();

        if(!doesExist){
            throw issueNotFound();
        }

        repository.update(issue.getId(), issue);

        return "Update Success";
    }

    public String deleteIssue(Issues issue){
        boolean doesExist = repository
                .findById(issue.getId())
                .isPresent();

        if(!doesExist){
            throw issueNotFound();
        }

        repository.delete(issue);

        return "Successfully Deleted";
    }

}
