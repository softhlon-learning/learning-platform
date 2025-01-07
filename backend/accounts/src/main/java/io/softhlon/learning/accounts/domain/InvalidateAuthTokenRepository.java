// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface InvalidateAuthTokenRepository {
    InvalidateAuthTokenResult execute(InvalidateAuthTokenRequest request);

    record InvalidateAuthTokenRequest(String authToken) {}
    sealed interface InvalidateAuthTokenResult {
        record TokenInvalidated() implements InvalidateAuthTokenResult {}
        record TokenInvalidationFailure(Throwable cause) implements InvalidateAuthTokenResult {}
    }
}
