// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import java.util.UUID;

@FunctionalInterface
public interface LoadAccountRepository {
    Result execute(Request request);

    record Request(UUID uuid) {}
    sealed interface Result {
        record Success(Account account) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }

    record Account(UUID id, String name, String email) {}
}
