package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.units.Units;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @SequenceGenerator(
            name = "userSequence",
            sequenceName = "userSequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "userSequence"
    )

    //Reference
    @Id
    private Long id;

    //Basic Info
    private String firstName;

    private String lastName;

    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    //Authority
    private boolean isLocked = true;

    private boolean isActive = false;

    private UserRoles userRole = UserRoles.inActive_User;

    private Boolean hasUnit = false;

    private String confirmCode;

    //Contact Info
    private String address;

    private String email;

    private String telephone;

    //Credentials
    private String username;

    private String password;

    //Rental info
    private LocalDate rentDue = null;

    private LocalDate rentLastPaid = null;

    private LocalDate dateLeaseStarted = null;

    private Long amountPaid = 0L;

    private Long amountOwed = 0L;

    private String rentalAddress = null;

    @OneToOne
    private Units usersUnit;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public User() {

    }

    public User(String firstName,
                String lastName,
                String fullName,
                LocalDate birthDate,
                String address,
                String email,
                String telephone,
                String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
    }

    public User(
            String firstName,
            String email,
            String username) {
        this.firstName = firstName;
        this.email = email;
        this.username = username;
    }
    public User(
            long id,
            String firstName,
            String email,
            String username) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.username = username;
    }

    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class UserLoginRequest{
        private final String username;
        private final String password;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class UnitUserRequest {
        private final Long id;
        private final String fullName;
        private final LocalDate birthDate;
        private final String email;
        private final String telephone;
        private final String username;
        private final UserRoles userRole;
        private final LocalDate rentDue;
        private final LocalDate rentLastPaid;
        private final LocalDate dateLeaseStarted;
        private final Long amountPaid;
        private final Long amountOwed;
    }
}
