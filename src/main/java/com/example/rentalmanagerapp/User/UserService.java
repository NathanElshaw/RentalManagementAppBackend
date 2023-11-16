package com.example.rentalmanagerapp.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String updateUser(User user){
        if(user == null){
            throw new IllegalStateException("Cannot update");
        }
        return "";
    }
}
