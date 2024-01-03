package com.example.rentalmanagerapp.rental.rentee.charges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChargesRepository extends JpaRepository<Charges, Long> {

    @Query("select c from Charges c" +
            " where c.chargeId = ?1 ")
    Optional<Charges> findByChargeId(String chargeId);

    @Query("select case when count(c) > 0 then " +
            "true else false end " +
            "from Charges c " +
            "where c = ?1 ")
    boolean assertChargeExists(Charges charge);

    @Modifying
    @Transactional
    @Query("update Charges c " +
            "set c = ?2 " +
            "where c.id = ?1 ")
    void updateCharge(Long chargeId, Charges charge);

    @Modifying
    @Transactional
    @Query("update Charges c " +
            "set c.amountPaid = ?2," +
            "c.amountOwed = ?3, " +
            "c.hasPaid = ?4," +
            "c.paidAt =  ?5," +
            "c.updatedAt = ?5 " +
            "where c.chargeId = ?1" )
    void makePayment(
            String chargeId,
            double paymentAmount,
            double amountOwed,
            boolean fullPayment,
            LocalDateTime paidAt);

}
