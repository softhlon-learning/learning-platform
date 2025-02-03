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
