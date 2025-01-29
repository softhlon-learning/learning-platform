// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface InitializeCheckoutService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded(String url) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          UUID acccountId,
          String email,
          String priceId) {}

}
