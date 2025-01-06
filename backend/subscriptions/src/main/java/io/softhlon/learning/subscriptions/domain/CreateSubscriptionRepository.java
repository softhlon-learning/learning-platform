// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@FunctionalInterface
interface CreateSubscriptionRepository {
    Result execute(Request request);

    record Request(UUID courseId, UUID accountId) {}
    sealed interface Result {
        record Success(UUID uuid) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
