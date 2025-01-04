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
public interface SignUpService {
    Response signUp(Request request);
    record Request(String name, String email, String password) {}
    record Response(boolean success) {}
}
