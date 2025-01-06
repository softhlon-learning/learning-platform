// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@FunctionalInterface
interface InvalidateAuthTokenRepository {
    Result execute(Request request);

    record Request(String authToken) {}
    sealed interface Result {
        record Success() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
