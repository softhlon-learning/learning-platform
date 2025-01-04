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
public interface SignInService {
    Response signIn(Request request);
    public record Request(String email, String password) {}
    public record Response(boolean success) {}
}
