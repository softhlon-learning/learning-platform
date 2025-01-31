// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface CollectStripeEventService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed() implements Result {}
    }

    record Request(String payload) {}

}
