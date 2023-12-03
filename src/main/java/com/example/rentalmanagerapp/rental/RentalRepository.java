package com.example.rentalmanagerapp.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental>findByRentalAddress(String address);

    @Transactional
    @Modifying
    @Query("update Rental r " +
            "set r.avgRentAmount = ?2, " +
            "r.totalRentIncome = ?3, " +
            "r.totalUnits = ?4 " +
            "where r.rentalAddress = ?1 ")
    void updateRentalOnNewUnit(
            String rentalAddress,
            double newAvgRent,
            double newRentalRent,
            int newRentalAmount);

    @Query("select r from Rental r")
    Optional<List<Rental>> getAllUnits();

}
