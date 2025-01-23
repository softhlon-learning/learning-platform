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
public interface CreateAccountRepository {
    CreateAccountResult execute(CreateAccountRequest request);

    sealed interface CreateAccountResult {
        record AccountPersisted(UUID uuid) implements CreateAccountResult {}
        record AccountPersistenceFailed(Throwable cause) implements CreateAccountResult {}
    }

    record CreateAccountRequest(
          String type,
          String name,
          String email,
          String password) {}
}
