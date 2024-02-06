package com.example.rentalmanagerapp.rental.issues;

import com.example.rentalmanagerapp.rental.units.Units;
import com.example.rentalmanagerapp.rental.units.UnitsDTOMapper;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class IssueDTOMapper implements Function<Issues.DtoReq, IssuesDTO> {

    private final UserDTOMapper dtoMapper;

    private final UnitsDTOMapper unitsDTOMapper;

    @Override
    public IssuesDTO apply(Issues.DtoReq request){

        Issues issues = request.getIssues();

        Units units = request.getUnit();

        User admin = request.getIssues().getCreatedBy();

        User user = request.getIssues().getSeenBy();

        return new IssuesDTO(
                dtoMapper.apply(admin),
                dtoMapper.apply(user),
                unitsDTOMapper.apply(units),
                issues.getIssuePriority(),
                issues.getIssueStatus(),
                issues.getIssueBody()
        );
    }
}
