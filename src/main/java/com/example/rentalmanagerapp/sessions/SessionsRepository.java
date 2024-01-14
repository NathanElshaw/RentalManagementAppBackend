package com.example.rentalmanagerapp.sessions;

import com.example.rentalmanagerapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionsRepository extends JpaRepository<Sessions, Long> {
    @Query("select s " +
            "from Sessions s " +
            "where s.user.id = ?1 ")
    Optional<Sessions> findByUserId(Long id);

    @Transactional
    @Modifying
    @Query("update Sessions s " +
            "set s = ?1 " +
            "where s.id = ?1 ")
    void updateUserSession(Long sessionId, Sessions newSession);

    @Transactional
    @Modifying
    @Query("update Sessions s " +
            "set s.isActive = ?2 " +
            "where s.user = ?1 ")
    void changeSessionStatus(User user, boolean bool);

}
