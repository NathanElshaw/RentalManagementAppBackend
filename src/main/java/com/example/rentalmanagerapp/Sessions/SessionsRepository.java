package com.example.rentalmanagerapp.Sessions;

import com.example.rentalmanagerapp.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, Long> {
    @Query("select s from Sessions s where s.user.id = ?1 ")
    Optional<Sessions> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("update Sessions s " +
            "set s.sessionAmount = ?2, s.lastSessionStart = ?3, s.startOfSession = ?4 " +
            "where s.user = ?1 ")
    void updateUserSession(User user, int sessionAmount, LocalDateTime lastSession, LocalDateTime sessionOfStart);


}
