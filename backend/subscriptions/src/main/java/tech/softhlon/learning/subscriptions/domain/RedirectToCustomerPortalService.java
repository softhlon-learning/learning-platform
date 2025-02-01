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
public interface RedirectToCustomerPortalService {

    Result execute(
          UUID account);

    sealed interface Result {
        record Succeeded(String url) implements Result {}
        record CustomerNotFound(String url) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
