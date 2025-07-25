// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist Stripe customer repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistCustomerRepository {

    /**
     * Persist Stripe customer in repository.
     * @param id         Primary key
     * @param customerId Customer Id
     * @param accountId  Account ID
     * @return PersistCustomerResult
     */
    PersistCustomerResult execute(
          UUID id,
          String customerId,
          UUID accountId);

    /**
     * Persist Stripe customer in repository result.
     */
    sealed interface PersistCustomerResult {
        record CustomerPersisted() implements PersistCustomerResult {}
        record CustomerPersistenceFailed(Throwable cause) implements PersistCustomerResult {}
    }

}
