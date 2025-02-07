// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist event log in repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistEventLogRepository {

    /**
     * Persist event log in repository.
     * @param eventType  Stripe event type
     * @param customerId Stripe customer id
     * @param payload    Stripe event payload
     * @return PersistEventLogResult
     */
    PersistEventLogResult execute(
          String eventType,
          String customerId,
          String payload);

    /**
     * Persist event log in repository result.
     */
    sealed interface PersistEventLogResult {
        record EventLogPersisted() implements PersistEventLogResult {}
        record EventLogPersistenceFailed(Throwable cause) implements PersistEventLogResult {}
    }

}
