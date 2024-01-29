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
                                .requestMatchers("/api/v*/user/delete").hasAnyRole(
                                        User.name(),
                                        Admin.name(),
                                        PropertyManger.name())

                                .requestMatchers(DELETE, "/api/v*/user/delete").hasAnyAuthority(
                                        User_Delete.name(),
                                        Admin_Delete.name(),
                                        PropertyManger.name())

                                .requestMatchers("/api/v*/user/getAll").hasAnyRole(
                                        Admin.name(),
                                        PropertyManger.name())

                                .requestMatchers(GET, "/api/v*/user/getAll").hasAnyAuthority(
                                        Admin_Read.name(),
                                        Property_Manager_Read.name())

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