// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load customer repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadCustomerRepository {

    /**
     * Load Stripe customer from repository.
     * @param customerId Customer Id
     * @return LoadCustomerResult
     */
    LoadCustomerResult execute(
          String customerId);

    /**
     * Load Stripe customer from repository result.
     */
    sealed interface LoadCustomerResult {
        record CustomerLoaded(Customer customer) implements LoadCustomerResult {}
        record CustomerNotFound() implements LoadCustomerResult {}
        record CustomerLoadFailed(Throwable cause) implements LoadCustomerResult {}
    }

    record Customer(
          UUID id,
          String customerId,
          UUID accountId) {}

}
