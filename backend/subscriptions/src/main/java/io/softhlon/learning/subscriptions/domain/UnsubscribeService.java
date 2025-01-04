// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UnsubscribeService {
    Result unsubscribe(Request request);

    record Request(String accountId) {}
    sealed interface Result {
        record Success() implements Result {}
        record AccountNotSubscribed() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
