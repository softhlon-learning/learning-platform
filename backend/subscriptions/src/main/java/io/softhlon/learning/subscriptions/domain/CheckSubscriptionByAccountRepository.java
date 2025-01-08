// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckSubscriptionByAccountRepository {
    CheckSubscriptionByAccountResult execute(CheckSubscriptionByAccountRequest request);

    record CheckSubscriptionByAccountRequest(UUID accountId) {}
    sealed interface CheckSubscriptionByAccountResult {
        record SubscriptionExists() implements CheckSubscriptionByAccountResult {}
        record SubscriptionNotFound() implements CheckSubscriptionByAccountResult {}
        record CheckSubscriptionFailed(Throwable cause) implements CheckSubscriptionByAccountResult {}
    }
}
