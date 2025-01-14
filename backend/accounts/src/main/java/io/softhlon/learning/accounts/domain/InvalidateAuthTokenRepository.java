// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface InvalidateAuthTokenRepository {
    InvalidateAuthTokenResult execute(InvalidateAuthTokenRequest request);

    sealed interface InvalidateAuthTokenResult {
        record TokenInvalidated() implements InvalidateAuthTokenResult {}
        record TokenInvalidationFailed(Throwable cause) implements InvalidateAuthTokenResult {}
    }
    record InvalidateAuthTokenRequest(String authToken) {}
}
