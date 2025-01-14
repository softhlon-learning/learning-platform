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
public interface CheckAccountByEmailRepository {
    CheckAccountByEmailResult execute(CheckAccountByEmailRequest request);

    sealed interface CheckAccountByEmailResult {
        record AccountExists() implements CheckAccountByEmailResult {}
        record AccountNotFound() implements CheckAccountByEmailResult {}
        record CheckAccountFailed(Throwable cause) implements CheckAccountByEmailResult {}
    }
    record CheckAccountByEmailRequest(String email) {}
}
