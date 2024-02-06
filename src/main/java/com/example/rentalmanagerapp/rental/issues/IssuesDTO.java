package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.issues.enums.IssuePriority;
import com.example.rentalmanagerapp.rental.issues.enums.IssueStatus;
import com.example.rentalmanagerapp.rental.units.UnitsDTO;
import com.example.rentalmanagerapp.user.UserDTO;

public record IssuesDTO (
        UserDTO createBy,

        UserDTO seenBy,

        UnitsDTO unit,

        IssuePriority issuePriority,

        IssueStatus issueStatus,

        String issueBody
){
}
