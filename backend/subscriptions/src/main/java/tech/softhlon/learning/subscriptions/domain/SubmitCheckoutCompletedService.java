// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SubmitCheckoutCompletedService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record CheckoutNotFound(String message) implements Result {}
        record IncorrectEventType(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          String sigHeader,
          String payload) {}

}
