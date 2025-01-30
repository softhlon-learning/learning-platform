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

    LoadCustomertResult execute(
          LoadCustomerRequest request);

    sealed interface LoadCustomertResult {
        record CustomerLoadLoaded(Customer customer) implements LoadCustomertResult {}
        record CustomerNotFound() implements LoadCustomertResult {}
        record CustomerLoadFailed(Throwable cause) implements LoadCustomertResult {}
    }

    record LoadCustomerRequest(
          String customerId) {}

    record Customer(
          UUID id,
          String customerId,
          UUID accountId) {}

}
