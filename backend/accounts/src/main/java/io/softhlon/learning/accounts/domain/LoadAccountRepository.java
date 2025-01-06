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
public interface LoadAccountRepository {
    Result execute(Request request);

    record Request(UUID id) {}
    sealed interface Result {
        record Success(Account account) implements Result {}
        record AccountNotFound() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }

    record Account(UUID id, String name, String email) {}
}
