// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckAccountByEmailRepository {
    CheckAccountByEmailResult execute(CheckAccountByEmailRequest request);

    record CheckAccountByEmailRequest(String email) {}
    sealed interface CheckAccountByEmailResult {
        record AccountExists() implements CheckAccountByEmailResult {}
        record AccountNotFound() implements CheckAccountByEmailResult {}
        record CheckAccountFailure(Throwable cause) implements CheckAccountByEmailResult {}
    }
}
