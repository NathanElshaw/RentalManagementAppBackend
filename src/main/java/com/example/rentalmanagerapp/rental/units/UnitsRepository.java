package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.unitcode.UnitCodes;
import com.example.rentalmanagerapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {

    @Query("select u from Units u " +
            "where u.renter = ?1")
    Optional<Units> getUnitByUserId(User userid);

    @Query("select u.unitCode " +
            "from Units u " +
            "where u.unitAddress = ?1 " +
            "and u.unitNumber = ?2 ")
    Optional<UnitCodes> getUnitCodeParent(String unitAddress, int unitNumber);

    @Query("select u from Units u " +
            "where u.renter = ?1 ")
    Optional<Units> findByUser(User user);


    @Query("select  u from Units u " +
            "where u.parentUnitId = ?1")
    Optional<List<Units>> getUnitsByParentId(Long parentId);

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.unitCode = ?1 " +
            "where u.unitAddress = ?2 and u.unitNumber = ?3")
    void addUnitCodeToRental(UnitCodes unitCodePayload, String unitAddress, int unitNumber);

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.renter = ?1," +
            "u.rentDue = ?3 " +
            "where u.id = ?2 ")
    void addRenterToUnit(User userId, Long unitId, double rentOwed);

    @Query("select u from Units u " +
            "where u.unitAddress = ?2 " +
            "and u.unitNumber = ?1")
    Optional<Units> findByUnitAddressAndUnitNumber(
            int unitNumber,
            String unitAddress);

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.unitAddress = ?2, " +
            "u.beds = ?3, " +
            "u.baths = ?4, " +
            "u.unitNumber = ?5, " +
            "u.hasPets = ?6, " +
            "u.rentAmount = ?7, " +
            "u.rentDue = ?8, " +
            "u.rentPaid = ?9, " +
            "u.leaseStart = ?10, " +
            "u.rentDueDate = ?11, " +
            "u.leaseEnd = ?12 " +
            "where u.id = ?1 ")
    void updateUnit(
            Long id,
            String address,
            int beds,
            double baths,
            int unitNumber,
            boolean hasPets,
            double rentAmount,
            double rentDue,
            double rentPaid,
            LocalDate leaseStart,
            LocalDate rentDueDate,
            LocalDate leaseEnd
    );

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.rentPaid = ?2, " +
            " u.rentDue = ?3 " +
            "where u.renter = ?1")
    void userPayment(User userId, double rentPaid, double rentDue);

    @Query("select u " +
            "from Units u " +
            "where u.unitAddress = ?1")
    Optional<List<Units>> getAllUnitByAddress(String address);

    @Query("select u from Units u")
    Optional<List<Units>> getAllUnits();
}
