// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
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
public interface LoadAccountRepository {
    LoadAccountResult execute(LoadAccountRequest request);

    record LoadAccountRequest(UUID id) {}
    sealed interface LoadAccountResult {
        record AccountLoaded(Account account) implements LoadAccountResult {}
        record AccountNotFound() implements LoadAccountResult {}
        record AccountLoadFailed(Throwable cause) implements LoadAccountResult {}
    }

    record Account(UUID id, String name, String email) {}
}
