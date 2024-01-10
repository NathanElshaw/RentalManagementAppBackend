package com.example.rentalmanagerapp.rental.unitcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UnitCodeRepository extends JpaRepository<UnitCodes, Long> {

    @Query("select c " +
            "from UnitCodes c " +
            "where c.unitCode = ?1 ")
    Optional<UnitCodes> findByUnitCode(String code);

    @Query("select case when count(u) > 0 then " +
            "true else false end " +
            "from UnitCodes  u " +
            "where u.unitCode = ?1 ")
    boolean assertByUnitCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE UnitCodes c " +
            "set c.confirmedAt = ?2 "
            + "WHERE c.unitCode = ?1 ")
    void updateConfirmedAt(String code,
                           LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query("update UnitCodes c " +
            "set c = ?2 " +
            "where c.id = ?1 ")
    void update(long id, UnitCodes update);
}
