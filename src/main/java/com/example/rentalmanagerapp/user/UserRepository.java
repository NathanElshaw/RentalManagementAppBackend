package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.units.Units;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("select u " +
            "from User u " +
            "where u.username = ?1 ")
    Optional<User> findByUsernameLogin(String username);

    @Transactional
    @Modifying
    @Query(" update  User  u " +
            "set u.usersUnit = ?2 " +
            "where u = ?1 ")
    void addUnitToUser(User user, Units unit);
}
