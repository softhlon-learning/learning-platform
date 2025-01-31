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
public interface LoadAccountRepository {

    LoadAccountResult execute(
          UUID accountId);

    sealed interface LoadAccountResult {
        record AccountLoaded(Account account) implements LoadAccountResult {}
        record AccountNotFound() implements LoadAccountResult {}
        record AccountLoadFailed(Throwable cause) implements LoadAccountResult {}
    }

    record Account(
          UUID id,
          String type,
          String name,
          String email,
          String password,
          boolean isDeleted) {}

}
