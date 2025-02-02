// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@FunctionalInterface
public interface PersistCustomersRepository {

    PersistCustomerResult execute(
          UUID id,
          String customerId,
          UUID accountId);

    sealed interface PersistCustomerResult {
        record CustomerPersisted() implements PersistCustomerResult {}
        record CustomerPersistenceFailed(Throwable cause) implements PersistCustomerResult {}
    }

}
