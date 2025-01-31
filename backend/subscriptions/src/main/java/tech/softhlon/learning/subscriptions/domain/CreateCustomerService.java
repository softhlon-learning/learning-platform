// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface CreateCustomerService {

    CreateCustomerResult execute(
          String customerId,
          UUID accountId);

    sealed interface CreateCustomerResult {
        record CustomerCreated() implements CreateCustomerResult {}
        record CustomerCreationFailed(Throwable cause) implements CreateCustomerResult {}
    }

}
