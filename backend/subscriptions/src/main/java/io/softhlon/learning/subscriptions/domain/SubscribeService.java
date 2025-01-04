// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface SubscribeService {
    Result subscribe(Request request);

    record Request(String accountId) {}
    sealed interface Result {
        record Success() implements Result {}
        record AccountAlreadySubscribed() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
