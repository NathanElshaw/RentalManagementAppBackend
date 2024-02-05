package com.example.rentalmanagerapp.rental;

import com.example.rentalmanagerapp.exceptions.BadRequestException;
import com.example.rentalmanagerapp.rental.units.UnitsRepository;
import com.example.rentalmanagerapp.user.User;
import com.example.rentalmanagerapp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {

    private final RentalRepository repository;

    private final UserRepository userRepository;

    private final UnitsRepository unitsRepository;

    private final UserRentalDTOMapper userRentalDTOMapper;

    private final AdminRentalDTOMapper adminRentalDTOMapper;

    private IllegalStateException rentalNotFound(){
        return new IllegalStateException("No Rentals Exist");
    }

    private IllegalStateException error(String s){
        return new IllegalStateException(s);
    }

    public String createRental(
            Rental rentalRequest){
        boolean addressExists = repository
                .assertRentalByAddress(
                        rentalRequest.getRentalAddress());

        if (addressExists) {
            throw error("Address already exists");
        }

        repository.save(rentalRequest);
        return "New Rental Saved";
    }

    //make dto for user req
    public RentalDTO getUserRental(Principal user){

        User reqUser = (User)
                ((UsernamePasswordAuthenticationToken) user)
                        .getPrincipal();

        boolean userHasRental = unitsRepository
                .assertUserHasRental(reqUser.getId());

        if(!userHasRental){
            throw new BadRequestException("User doesnt have a unit");
        }

        Rental usersRental = repository
                .findByRentalAddress(reqUser.getRentalAddress())
                .orElseThrow(this::rentalNotFound);

        Rental.Dto transferObject = new Rental.Dto(
                usersRental,
                reqUser.getUsersUnit()
        );

        return userRentalDTOMapper.apply(transferObject);
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

    public String deleteRental(Rental rental){
        boolean rentalExists = repository
                .assertRentalByAddress(rental.getRentalAddress());

        if(!rentalExists){
            throw rentalNotFound();
        }

        repository.delete(rental);

        return "Success";
    }


    public List<RentalDTO.AdminRentalDTO> getAllRentals(){

        List<RentalDTO.AdminRentalDTO> returnRentalList = new ArrayList<>();
        List<Rental> rentals = repository.getAllUnits();

         rentals.forEach(listRental -> {
            returnRentalList.add(adminRentalDTOMapper
                    .apply(listRental));
        });

        return returnRentalList;
    }

    public List<RentalDTO.AdminRentalDTO> getPropertyMangerRentals(User user){

        List<RentalDTO.AdminRentalDTO> returnedList =
                new ArrayList<>();

         User manager = userRepository
                 .findById(user.getId())
                 .orElseThrow(()->
                         new IllegalStateException("User not found")
        );

         List<Rental> getManagedRentals = repository
                 .getRentalByAssignedManager(manager);

         getManagedRentals.forEach(rental ->
                 {
                     returnedList.add(adminRentalDTOMapper.apply(
                             rental
                     ));
                 }
         );

         return returnedList;
    }

}
