package com.example.rentalmanagerapp.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    Admin_Read("admin:read"),

    Admin_Update("admin:update"),

    Admin_Post("admin:post"),

    Admin_Delete("admin:delete"),

    Property_Manager_Read("property_manager:read"),

    Property_Manager_Update("property_manager:update"),

    Property_Manager_Post("property_manager:post"),

    Property_Manager_Delete("property_manager:delete"),

    User_Read("user:read"),

    User_Update("user:update"),

    User_Post("user:post"),

    User_Delete("user:delete"),

    Inactive_User_Post("inactive_user:post");

    @Getter
    private final String permissions;
}
