package com.example.rentalmanagerapp.registration;

public interface EmailSender{

    default void send(String to, String code){

    }

}
