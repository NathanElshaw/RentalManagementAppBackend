package com.example.rentalmanagerapp.Rental.Units;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {
    @Query(value = "select u from Units u where u.unitAddress = ?2 and u.unitNumber = ?1")
    Optional<Units> findByUnitAddressAndUnitNumber(int unitNumber, String unitAddress);
}
