package com.example.rentalmanagerapp.Rental.RentalCodes;

import lombok.AllArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UnitCodeRepository extends JpaRepository<UnitCodes, Long> {
    Optional<UnitCodes> findByUnitCode(String code);

    //Set query here Select token from UnitCode and update confirmedat with confrimedAt date
    int updateConfirmedAt(String code,
                          LocalDateTime confirmedAt);
}
