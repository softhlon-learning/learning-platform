// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@FunctionalInterface
public interface CreateAccountRepository {
    Result execute(Request request);

    record Request(String name, String email, String password) {}
    sealed interface Result {
        record Success(UUID uuid) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
