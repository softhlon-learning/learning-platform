// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public class AuthenticationContext {

    private static final String MOCKED_ID = "0053d4b9-ecbf-4ebb-b6a0-d7abdc91a0f3";

    @Value("${authentication.mocked:false}")
    private Boolean authenticationMocked;

    @Value("${authentication.mocked-id}")
    private String mockedAccountId;

    public UUID accountId() {

        if (authenticationMocked) {
            return UUID.fromString(mockedAccountId);
        } else {
            var authentication = SecurityContextHolder
                  .getContext()
                  .getAuthentication();

            if (authentication instanceof AuthenticationToken) {
                var authToken = (AuthenticationToken) SecurityContextHolder
                      .getContext()
                      .getAuthentication();
                return authToken.getAccountId();
            } else {
                return null;
            }
        }

    }

    public String email() {

        var authentication = SecurityContextHolder
              .getContext()
              .getAuthentication();

        if (authentication instanceof AuthenticationToken) {
            var authToken = (AuthenticationToken) SecurityContextHolder
                  .getContext()
                  .getAuthentication();
            return authToken.getName();
        } else {
            return null;
        }

    }

}
