package com.example.rentalmanagerapp.Rental.RentalCodes;

import com.example.rentalmanagerapp.Rental.Rental;
import com.example.rentalmanagerapp.Rental.Units.Units;
import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
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
    int updateConfirmedAt(String code,
                          LocalDateTime confirmedAt);

    @Query("select rental_id from UnitCodes where unitCode = ?1")
    Long returnUnitWithCode(String code);

}
