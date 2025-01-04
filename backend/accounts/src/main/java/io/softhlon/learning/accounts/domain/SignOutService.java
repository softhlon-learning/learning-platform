// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.common.hexagonal.InboundPort;

@InboundPort
@FunctionalInterface
public interface SignOutService {
    Result signOut(Request request);

    record Request(String authenticationToken) {}
    sealed interface Result {
        record Success() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
