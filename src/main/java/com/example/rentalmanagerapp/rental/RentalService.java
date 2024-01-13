package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {

    private final RentalRepository repository;

    private final UserRepository userRepository;

    private IllegalStateException rentalNotFound(){
        return new IllegalStateException("No Rentals Exist");
    }

    private IllegalStateException error(String s){
        return new IllegalStateException(s);
    }

    public String createRental(
            Rental rentalRequest
    ){
        boolean addressExists = repository
                .assertRentalByAddress(
                        rentalRequest.getRentalAddress());

        if (addressExists) {
            throw error("Address already exists");
        }

        repository.save(rentalRequest);
        return "New Rental Saved";
    }

    public String updateRental(
            Rental updateRental){
        //Todo reduce this;
        Rental targetRental =
                repository.findById(
                updateRental.getId())
                .orElseThrow(this::rentalNotFound);

        updateRental.setUpdatedAt(LocalDateTime.now());

        repository.updateRental(
                updateRental.getId(),
                targetRental);

        return "Success";
    }

    public String deleteRental(Long rentalId){
        repository.findById(rentalId)
                .orElseThrow(this::rentalNotFound);

        repository.deleteById(rentalId);

        return "Success";
    }


    public List<Rental> getAllRentals(){

        List<Rental> returnRentalList = new ArrayList<>();
        List<Rental> rentals = repository.getAllUnits();

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

         List<Rental> getManagedRentals = repository.getRentalByAssignedManager(manager);

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
