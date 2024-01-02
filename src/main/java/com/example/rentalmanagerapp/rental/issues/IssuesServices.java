package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
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

    private RuntimeException issueNotFound (){
        return new IllegalStateException("Issue not found");
    }

    public List<Issues> getAllIssues(){
        return repository.findAll();
    }


    public int checkForIssues(User user){
        return repository.getIssueAmount(user);
    }

    public void createIssue(Issues issue){
        repository.save(issue);
    }

    public List<Issues> getRentalIssues(String rentalAddress){
        List<Issues> returnList = new ArrayList<>();

        List<Issues> rentalsIssues = repository
                .getRentalsIssuesByAddress(rentalAddress);

        if(rentalsIssues.isEmpty()){
            throw new BadRequestException("No issues found");
        }


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
                .assertIssueExists(issue);

        if(!doesExist){
            throw issueNotFound();
        }

        repository.update(issue.getId(), issue);

        return "Update Success";
    }

    public String deleteIssue(Issues issue){
        boolean doesExist = repository
                .assertIssueExists(issue);

        if(!doesExist){
            throw issueNotFound();
        }

        repository.delete(issue);

        return "Successfully Deleted";
    }

}
