// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UnsubscribeService {
    Result unsubscribe(Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotSubscribedFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
    record Request(String accountId) {}
}
