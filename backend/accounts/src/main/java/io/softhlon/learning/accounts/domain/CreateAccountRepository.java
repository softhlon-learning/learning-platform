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
public interface CreateAccountRepository {
    CreateAccountResult execute(CreateAccountRequest request);

    record CreateAccountRequest(
          String name,
          String email,
          String password,
          String status) {}

    sealed interface CreateAccountResult {
        record AccountPesisted(UUID uuid) implements CreateAccountResult {}
        record AccountPersistenceFailure(Throwable cause) implements CreateAccountResult {}
    }
}
