package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.User;
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

    @Transactional
    @Modifying
    @Query("update Rental  r " +
            "set r.totalRentIncome = ?2 " +
            "where r.id = ?1 ")
    void updateRentalIncome(Long id, double newIncome);

    @Transactional
    @Modifying
    @Query("update  Rental  r " +
            "set r.assignedManager = ?2 " +
            "where r.id = ?1")
    void addManagerToRental(Long id, User manager);

    @Transactional
    @Modifying
    @Query("update Rental r " +
            "set r.assignedManager = ?2 " +
            "where r.rentalAddress = ?1")
    void addManagerToComplex(String address, User manager);

    @Query("select r from Rental r where r.assignedManager = ?1 ")
    Optional<List<Rental>> getRentalByAssignedManager(User user);

    @Query("select r from Rental r")
    Optional<List<Rental>> getAllUnits();

}
