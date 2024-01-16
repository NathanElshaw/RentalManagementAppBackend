package com.example.rentalmanagerapp.user;

import com.example.rentalmanagerapp.rental.units.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query("select u " +
            "from User u " +
            "where u.email = ?1 ")
    Optional<User> findByEmail(String email);

    @Query("select case when count(u) > 0 then " +
            "true else false end " +
            "from User u " +
            "where u.email = ?1 ")
    boolean selectIfEmailExists(String email);

    @Query("select  case when counts(u) > 0 then " +
            "true else false end " +
            "from User u " +
            "where u.id = ?1 ")
    boolean assertUserExists(Long userId);

    @Query("select u " +
            "from User u " +
            "where u.username = ?1 ")
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update User u " +
            "set u = ?2 " +
            "where u.id = ?1")
    void updateUser(Long userId, User newUserInfo);

    @Transactional
    @Modifying
    @Query(" update  User  u " +
            "set u.usersUnit = ?2 " +
            "where u = ?1 ")
    void addUnitToUser(User user, Units unit);
}
