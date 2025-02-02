// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
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

    /**
     * Check if given token hash exists in repository.
     * @param tokenHash Token hash to be checked
     * @return CheckTokenResult
     */
    CheckTokenResult execute(
          String tokenHash);

    /**
     * Check token hash result.
     */
    sealed interface CheckTokenResult {
        record TokenExists() implements CheckTokenResult {}
        record TokenNotFound() implements CheckTokenResult {}
        record CheckTokenFailed(Throwable cause) implements CheckTokenResult {}
    }

}
