package com.example.rentalmanagerapp.security.config;

import com.example.rentalmanagerapp.user.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/error",
                            "/api/v*/user/register/**")
                            .permitAll();
                })

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                                    "/admin/**",
                                    "/api/v1/**")
                        .hasAuthority(String.valueOf(UserRoles.Admin));
                })

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/user",
                                    "/api/v1/rentals/user/getRental",
                                    "/api/v1/units/userIdGetRental",
                                    "/api/v1/unitCodes/joinUnit",
                                    "/api/v1/charges/user/**",
                                    "/api/v1/issues/create",
                                    "/api/v1/issues/update"
                                    )
                            .hasRole(String.valueOf(UserRoles.User));
                })

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())
                .build();
    }
}
