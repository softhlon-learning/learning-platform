// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import java.util.UUID;

interface CreateSubscriptionService {

    CreateSubscriptionResult execute(
          CreateSubscriptionRequest request);

    record CreateSubscriptionRequest(UUID accountId) {}

    sealed interface CreateSubscriptionResult {
        record SubscriptionCreated() implements CreateSubscriptionResult {}
        record SubscriptionCreationFailed(Throwable cause) implements CreateSubscriptionResult {}
    }

}
