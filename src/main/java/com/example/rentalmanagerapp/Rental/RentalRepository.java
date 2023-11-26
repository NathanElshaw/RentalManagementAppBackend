package com.example.rentalmanagerapp.Rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental>findByRentalAddress(String address);
    
}
