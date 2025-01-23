// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckAccountByEmailRepository {
    CheckAccountByEmailResult execute(CheckAccountByEmailRequest request);

    sealed interface CheckAccountByEmailResult {
        record AccountExists(UUID id) implements CheckAccountByEmailResult {}
        record AccountIsDeleted() implements CheckAccountByEmailResult {}
        record AccountNotFound() implements CheckAccountByEmailResult {}
        record CheckAccountFailed(Throwable cause) implements CheckAccountByEmailResult {}
    }

    record CheckAccountByEmailRequest(String email) {}
}
