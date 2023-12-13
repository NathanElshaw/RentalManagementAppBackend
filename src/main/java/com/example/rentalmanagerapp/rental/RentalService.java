package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    public String createRental(
            RentalRequest rentalRequest
    ){
        boolean addressExists = rentalRepository.findByRentalAddress(
                rentalRequest.getRentalAddress()).isPresent();

        if (addressExists) {
            throw new IllegalStateException("Address already exists");
        }

        Rental newRental = new Rental(
                rentalRequest.getRentalAddress(),
                rentalRequest.getDescription(),
                rentalRequest.getType(),
                LocalDate.parse(
                        rentalRequest.getDateAvailable()
                )
        );

        rentalRepository.save(newRental);
        return "New Rental Saved";
    }

    public List<Rental> getAllRentals(){
        List<Rental> returnRentalList = new ArrayList<>();
        List<Rental> rentals = rentalRepository.getAllUnits()
                .orElseThrow(
                ()->new IllegalStateException("No Rentals Exist")
                );

         rentals.forEach(listRental -> {
            Rental returnRental = new Rental(
                    listRental.getId(),
                    listRental.getRentalAddress(),
                    listRental.getDescription(),
                    listRental.getType(),
                    listRental.getTotalTenants(),
                    listRental.getTotalUnits(),
                    listRental.getAvgRentAmount(),
                    listRental.getTotalRentIncome(),
                    listRental.getAssignedManager(),
                    listRental.getCreatedBy(),
                    listRental.getCreatedAt(),
                    listRental.getUpdatedAt()
            );

            returnRentalList.add(returnRental);

        });

        return returnRentalList;
    }

    public List<Rental> getPropertyMangerRentals(Long userId){

        List<Rental> returnedList = new ArrayList<>();

         User manager = userRepository.findById(userId).orElseThrow(
                ()->new IllegalStateException("User not found")
        );

         List<Rental> getManagedRentals = rentalRepository.getRentalByAssignedManager(manager)
                 .orElseThrow(
                         ()->new IllegalStateException("User doesnt manage any rentals")
         );

         getManagedRentals.forEach(rental ->
                 {
                     Rental managedRental = new Rental(
                             rental.getId(),
                             rental.getRentalAddress(),
                             rental.getDescription(),
                             rental.getType(),
                             rental.getTotalTenants(),
                             rental.getTotalUnits(),
                             rental.getAssignedManager(),
                             rental.getCreatedBy(),
                             rental.getUpdatedAt()
                     );

                     returnedList.add(managedRental);
                 }
         );

         return returnedList;
    }

}
