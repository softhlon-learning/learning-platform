// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            throw new UnsupportedOperationException();
        }
    }
}
