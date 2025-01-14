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
    CheckAuthTokenResult execute(CheckAuthTokenRequest request);

    sealed interface CheckAuthTokenResult {
        record AuthTokenExists() implements CheckAuthTokenResult {}
        record AuthTokenNotFound() implements CheckAuthTokenResult {}
        record CheckAuthTokenFailed(Throwable cause) implements CheckAuthTokenResult {}
    }
    record CheckAuthTokenRequest(String authTokenHash) {}
}
