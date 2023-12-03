package com.example.rentalmanagerapp.rental.units;

import com.example.rentalmanagerapp.rental.rentalCodes.UnitCodes;
import com.example.rentalmanagerapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Long> {

    @Query("select u from Units u where u.renter = ?1")
    Optional<Units> getUnitByUserId(User userid);

    @Query("select u.unitCode from Units u where u.unitAddress = ?1 and u.unitNumber = ?2 ")
    Optional<UnitCodes> getUnitCodeParent(String unitAddress, int unitNumber);

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.unitCode = ?1 " +
            "where u.unitAddress = ?2 and u.unitNumber = ?3")
    void addUnitCodeToRental(UnitCodes unitCodePayload, String unitAddress, int unitNumber);

    @Transactional
    @Modifying
    @Query("update Units u " +
            "set u.renter = ?1 " +
            "where u.id = ?2 ")
    void addRenterToUnit(User userId, Long unitId);

    @Query("select u from Units u " +
            "where u.unitAddress = ?2 " +
            "and u.unitNumber = ?1")
    Optional<Units> findByUnitAddressAndUnitNumber(
            int unitNumber,
            String unitAddress);

    @Query("select u " +
            "from Units u " +
            "where u.unitAddress = ?1")
    Optional<List<Units>> getAllUnitByAddress(String address);

    @Query("select u from Units u")
    Optional<List<Units>> getAllUnits();
}
