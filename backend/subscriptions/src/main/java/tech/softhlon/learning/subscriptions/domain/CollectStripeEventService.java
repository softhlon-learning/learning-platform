// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface CollectStripeEventService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          String sigHeader,
          String payload) {}

}
