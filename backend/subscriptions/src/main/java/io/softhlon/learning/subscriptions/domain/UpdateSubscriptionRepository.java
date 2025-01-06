// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@FunctionalInterface
interface UpdateSubscriptionRepository {
    Result execute(Subscription subscription);

    record Subscription(
          UUID id,
          UUID accountId,
          OffsetDateTime startedTime,
          OffsetDateTime cancelledTime) {}

    sealed interface Result {
        record Success() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
