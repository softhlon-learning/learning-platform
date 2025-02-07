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
 * Check active subscription for account service interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckSubscriptionByAccountRepository {

    /**
     * Check if given account has active subscription.
     * @param accountId Account Id
     * @return CheckSubscriptionByAccountResult
     */
    CheckSubscriptionByAccountResult execute(
          UUID accountId);

    /**
     * Check if given account has active subscription result.
     */
    sealed interface CheckSubscriptionByAccountResult {
        record SubscriptionExists() implements CheckSubscriptionByAccountResult {}
        record SubscriptionNotFound() implements CheckSubscriptionByAccountResult {}
        record CheckSubscriptionFailed(Throwable cause) implements CheckSubscriptionByAccountResult {}
    }

}
