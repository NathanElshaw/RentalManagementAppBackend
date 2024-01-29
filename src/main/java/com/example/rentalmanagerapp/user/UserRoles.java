package com.example.rentalmanagerapp.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.rentalmanagerapp.user.Permissions.*;

@Getter
@RequiredArgsConstructor
public enum UserRoles {

    User(
            Set.of(
                    User_Read,
                    User_Post,
                    User_Update,
                    User_Delete
            )
    ),

    Admin(
            Set.of(
                    User_Read,
                    User_Post,
                    User_Update,
                    User_Delete,
                    Admin_Read,
                    Admin_Post,
                    Admin_Update,
                    Admin_Delete,
                    Property_Manager_Read,
                    Property_Manager_Post,
                    Property_Manager_Update,
                    Property_Manager_Delete
            )
    ),

    inActive_User(
            Set.of(
                    Inactive_User_Post
            )
    ),

    PropertyManger(
            Set.of(
                    User_Read,
                    User_Post,
                    User_Update,
                    User_Delete,
                    Property_Manager_Read,
                    Property_Manager_Post,
                    Property_Manager_Update,
                    Property_Manager_Delete
            )
    )

    ;
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
       List<SimpleGrantedAuthority> authorities =
               new java.util.ArrayList<>(getPermissions()
                       .stream()
                       .map(permissions -> new SimpleGrantedAuthority(
                               permissions.name()
                       ))
                       .toList());

       authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

       return authorities;
    };
}
