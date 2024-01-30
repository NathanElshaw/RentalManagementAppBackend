package com.example.rentalmanagerapp.security.config;

import com.example.rentalmanagerapp.security.PasswordEncoder;
import com.example.rentalmanagerapp.security.filter.JwtAuthFilter;
import com.example.rentalmanagerapp.user.UserRoles;
import com.example.rentalmanagerapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sound.midi.Patch;

import static com.example.rentalmanagerapp.user.Permissions.*;
import static com.example.rentalmanagerapp.user.UserRoles.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private  final String[] urlList = {
            "/api/v*/user/register",
            "/api/v*/user/login"
    };

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService.userDetailsService());

        authProvider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(urlList)
                                .permitAll()

                                //User Endpoints

                                .requestMatchers("/api/v*/user/delete").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name())

                                .requestMatchers(DELETE, "/api/v*/user/delete").hasAnyAuthority(
                                        User_Delete.name(),
                                        Admin_Delete.name(),
                                        PropertyManger.name())

                                .requestMatchers("/api/v*/user/update").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name())

                                .requestMatchers(PATCH, "api/v*/user/update").hasAnyAuthority(
                                        User_Delete.name(),
                                        Admin_Delete.name(),
                                        PropertyManger.name())

                                .requestMatchers("/api/v*/user/getAll").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name())

                                .requestMatchers(GET, "/api/v*/user/getAll").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name())

                                //Rental Endpoints

                                .requestMatchers("/api/v*/rentals/create").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/rentals/create").hasAnyAuthority(
                                        Admin_Post.name(),
                                        Property_Manager_Post.name()
                                )

                                .requestMatchers("api/v*/rentals/getAll").hasAnyRole(
                                        Admin.name()
                                )

                                .requestMatchers(GET, "api/v&/rentals/getAll").hasAnyAuthority(
                                        Admin_Read.name()
                                )

                                .requestMatchers("api/v*/rentals/user/getRental").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/rentals/user/getRental").hasAnyAuthority(
                                        User_Read.name(),
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                .requestMatchers("api/v*/rentals/manager/getUnits").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/rentals/manage/getUnits").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                .requestMatchers("api/v*/rentals/update").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(PATCH, "api/v*/rentals/update").hasAnyAuthority(
                                        Admin_Update.name(),
                                        Property_Manager_Update.name()
                                )

                                .requestMatchers("api/v*/rentals/delete").hasAnyRole(
                                        Admin.name()
                                )

                                .requestMatchers(DELETE, "api/v*/rentals/delete").hasAnyAuthority(
                                        Admin_Delete.name()
                                )

                                //Unit Endpoints

                                .requestMatchers("api/v*/units/create").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/units/create").hasAnyAuthority(
                                        Admin_Post.name(),
                                        Property_Manager_Post.name()
                                )

                                .requestMatchers("api/v*/units/getRental").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/units/getRental").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                .requestMatchers("api/v*/units/update").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(PATCH, "api/v*/units/update").hasAnyAuthority(
                                        Admin_Update.name(),
                                        Property_Manager_Update.name()
                                )

                                .requestMatchers("api/v*/units/getAllUnitsByAddress").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/units/getAllUnitsByAddress").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                .requestMatchers("api/v*/units/getAllUnits").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/units/getAllUnits").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                .requestMatchers("api/v*/units/userIdGetRental").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(GET, "api/v*/units/userIdGetRental").hasAnyAuthority(
                                        User_Read.name(),
                                        Admin_Read.name(),
                                        Property_Manager_Read.name()
                                )

                                //Unit code Endpoints

                                .requestMatchers("api/v*/unitCodes/create").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/unitCodes/create").hasAnyAuthority(
                                        Admin_Post.name(),
                                        Property_Manager_Post.name()
                                )

                                .requestMatchers("api/v*/unitCodes/joinUnit").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(PATCH, "api/v*/unitCodes/joinUnit").hasAnyAuthority(
                                        User_Update.name(),
                                        Admin_Update.name(),
                                        Property_Manager_Update.name()
                                )

                                .requestMatchers("api/v*/unitCodes/update").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/unitCodes/update").hasAnyAuthority(
                                        Admin_Update.name(),
                                        Property_Manager_Update.name()
                                )

                                .requestMatchers("api/v*/unitCodes/delete").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/unitCodes/delete").hasAnyAuthority(
                                        Admin_Delete.name(),
                                        Property_Manager_Delete.name()
                                )

                                //Issues

                                .requestMatchers("api/v*/issues/create").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(POST, "api/v*/issues/create").hasAnyAuthority(
                                        User_Post.name(),
                                        Admin_Post.name(),
                                        Property_Manager_Post.name()
                                )

                                .requestMatchers("api/v*/issues/update").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(PATCH, "api/v*/issues/update").hasAnyAuthority(
                                        User_Update.name(),
                                        Admin_Update.name(),
                                        Property_Manager_Update.name()
                                )

                                .requestMatchers("/api/v*/issues/delete").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name()
                                )

                                .requestMatchers(DELETE, "/api/v*/issues/delete").hasAnyAuthority(
                                        User_Delete.name(),
                                        Admin_Delete.name(),
                                        Property_Manager_Delete.name()
                                )

                                .anyRequest()
                                .authenticated()

                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}

//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authProvider())
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(withDefaults())
//                .build();
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(
//                                    "/error",
//                                    "/api/v*/user/register/**",
//                                    "/api/v*/user/login")
//                            .permitAll()
//                            .anyRequest().authenticated();

//                    auth.requestMatchers(
//                                    "/admin/**",
//                                    "/api/v1/**")
//                            .hasAuthority(String.valueOf(UserRoles.Admin))
//                            .anyRequest().authenticated();
//
//                    auth.requestMatchers("/api/v1/user",
//                                    "/api/v1/rentals/user/getRental",
//                                    "/api/v1/units/userIdGetRental",
//                                    "/api/v1/unitCodes/joinUnit",
//                                    "/api/v1/charges/user/**",
//                                    "/api/v1/issues/create",
//                                    "/api/v1/issues/update"
//                            )
//                            .hasRole(String.valueOf(UserRoles.User))
//                            .anyRequest().authenticated();
//                })