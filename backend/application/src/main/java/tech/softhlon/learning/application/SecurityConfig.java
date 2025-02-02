// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
          HttpSecurity http) throws Exception {

        return http
              .csrf(csrf -> csrf.disable())
              .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                          "/api/v1/account/auth/**",
                          "/api/v1/account/sign-up",
                          "/api/v1/account/reset-password",
                          "/api/v1/account/update-password",
                          "/api/v1/course",
                          "/api/v1/subscription/checkout-completed-event",
                          "/api/v1/subscription/customer-created-event",
                          "/api/v1/subscription/created-event",
                          "/api/v1/subscription/updated-event",
                          "/api/v1/subscription/deleted-event",
                          "/api/v1/subscription/invoice-paid-event",
                          "/api/v1/subscription/generic-event"
                    ).permitAll()
                    .anyRequest().authenticated())
              .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authenticationManager(authenticationManager)
              .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
              .build();

    }

}
