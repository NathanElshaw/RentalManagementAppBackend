package com.example.rentalmanagerapp.User.Registration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationReposity extends JpaRepository<Registration, Registration> {
    public String findByEmail(email);

}
