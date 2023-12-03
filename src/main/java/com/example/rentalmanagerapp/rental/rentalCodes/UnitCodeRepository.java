package com.example.rentalmanagerapp.rental.rentalCodes;

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
    Optional<UnitCodes> findByUnitCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE UnitCodes c " +
            "set c.confirmedAt = ?2 "
            + "WHERE c.unitCode = ?1 ")
    void updateConfirmedAt(String code,
                          LocalDateTime confirmedAt);

    @Query("select parentRental from UnitCodes where unitCode = ?1")
    Long getUnitWithCode(String code);

}
