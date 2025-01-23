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
public interface CheckTokenRepository {
    CheckTokenResult execute(CheckTokenRequest request);

    sealed interface CheckTokenResult {
        record TokenExists() implements CheckTokenResult {}
        record TokenNotFound() implements CheckTokenResult {}
        record CheckTokenFailed(Throwable cause) implements CheckTokenResult {}
    }

    record CheckTokenRequest(String tokenHash) {}
}
