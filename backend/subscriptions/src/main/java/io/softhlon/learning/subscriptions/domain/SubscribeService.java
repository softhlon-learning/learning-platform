// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface SubscribeService {
    Result subscribe(Request request);

    record Request(UUID accountId) {}
    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountAlreadySubscribedFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}
