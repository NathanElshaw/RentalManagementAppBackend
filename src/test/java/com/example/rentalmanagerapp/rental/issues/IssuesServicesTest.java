package com.example.rentalmanagerapp.rental.issues;


import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.rentalmanagerapp.globals.GlobalVars.*;
import static com.example.rentalmanagerapp.rental.issues.enums.IssuePriority.Low;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
class IssuesServicesTest {

    @Mock
    private IssuesRepository issuesRepository;

    private AutoCloseable autoCloseable;


    private IssuesServices underTest;

    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new IssuesServices(issuesRepository);

    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void canGetAll() {

        underTest.getAllIssues();

        verify(issuesRepository).findAll();

    }

    @Test
    void canCheckForIssues() {

        User user = new User(
                name,
                email,
                username
        );

        underTest.checkForIssues(user);

        given(issuesRepository.getIssueAmount(user)).willReturn(2);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(issuesRepository).getIssueAmount(userArgumentCaptor.capture());

        assertThat(underTest.checkForIssues(user)).isEqualTo(2);

    }

    @Test
    void canCreateIssue() {
        Issues issue = new Issues(
                "Issue",
                Low,
                32
        );

        underTest.createIssue(issue);

        ArgumentCaptor<Issues> issuesArgumentCaptor = ArgumentCaptor.forClass(Issues.class);

        verify(issuesRepository)
                .save(issuesArgumentCaptor.capture());

        Issues capturedIssue = issuesArgumentCaptor.getValue();

        assertThat(capturedIssue).isEqualTo(issue);
    }

    @Test
    void canGetRentalIssues() {

        List<Issues> fillerList = new ArrayList<>();

        User user = new User(
                name,
                email,
                username
        );

        Issues issue = new Issues(
                user,
                "Issue",
                Low
        );

       fillerList.add(issue);

        given(issuesRepository
                .getRentalsIssuesByAddress(address))
                .willReturn(fillerList);

        underTest.getRentalIssues(address);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);

        verify(issuesRepository)
                .getRentalsIssuesByAddress(
                        stringArgumentCaptor.capture());

        assertThat(underTest.getRentalIssues(address).size()).isEqualTo(1);
    }

    @Test
    void getRentalIssueWillThrow(){

        given(issuesRepository
                .getRentalsIssuesByAddress(address))
                .willReturn(new ArrayList<>());

        assertThatThrownBy(()->underTest.getRentalIssues(address))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("No issues found");
    }

    @Test
    void canUpdateIssue() {
        User user = new User(
                name,
                email,
                username
        );

        Issues issue = new Issues(
                user,
                "Issue",
                Low
        );

        given(issuesRepository
                .assertIssueExists(issue))
                .willReturn(true);

        underTest.updateIssue(issue);

        ArgumentCaptor<Long> stringArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Issues> issuesArgumentCaptor = ArgumentCaptor.forClass(Issues.class);

        verify(issuesRepository)
                .update(
                        stringArgumentCaptor.capture(),
                        issuesArgumentCaptor.capture());

        assertThat(underTest.updateIssue(issue)).isEqualTo("Update Success");

    }

    @Test
    void updateIssueWillThrow(){
        User user = new User(
                name,
                email,
                username
        );

        Issues issue = new Issues(
                user,
                "Issue",
                Low
        );

        given(issuesRepository
                .assertIssueExists(issue))
                .willReturn(false);


        assertThatThrownBy(()->underTest.updateIssue(issue)).isInstanceOf(IllegalStateException.class).hasMessage("Issue not found");
    }

    @Test
    void canDeleteIssue() {

        User user = new User(
                name,
                email,
                username
        );


        Issues issue = new Issues(
                user,
                "Issue",
                Low
        );

        given(issuesRepository
                .assertIssueExists(issue))
                .willReturn(true);

        underTest.deleteIssue(issue);

        ArgumentCaptor<Issues> issuesArgumentCaptor = ArgumentCaptor.forClass(Issues.class);

        verify(issuesRepository)
                .delete(
                        issuesArgumentCaptor.capture());

        assertThat(underTest.deleteIssue(issue)).isEqualTo("Successfully Deleted");

    }

    @Test
    void deleteIssueWillThrow() {

        User user = new User(
                name,
                email,
                username
        );

        Issues issue = new Issues(
                user,
                "Issue",
                Low
        );

        given(issuesRepository
                .assertIssueExists(issue))
                .willReturn(false);


        assertThatThrownBy(()->underTest.deleteIssue(issue)).isInstanceOf(IllegalStateException.class).hasMessage("Issue not found");

    }

}