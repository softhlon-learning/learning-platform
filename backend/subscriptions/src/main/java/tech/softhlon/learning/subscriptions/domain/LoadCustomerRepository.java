// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCustomerRepository {

    LoadCustomerResult execute(
          String customerId);

    sealed interface LoadCustomerResult {
        record CustomerLoadLoaded(Customer customer) implements LoadCustomerResult {}
        record CustomerNotFound() implements LoadCustomerResult {}
        record CustomerLoadFailed(Throwable cause) implements LoadCustomerResult {}
    }

    record Customer(
          UUID id,
          String customerId,
          UUID accountId) {}

}
