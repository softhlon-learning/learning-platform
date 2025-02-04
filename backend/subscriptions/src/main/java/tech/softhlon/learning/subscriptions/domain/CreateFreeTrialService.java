// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create free trial service interface.
 */
@InboundPort
@FunctionalInterface
public interface CreateFreeTrialService {

    /**
     * Create free trial.
     * @param accountId Account Id
     * @param expireAt  Free trial expiration time
     * @return Result
     */
    Result execute(
          UUID accountId,
          OffsetDateTime expireAt);

    /**
     * Create free trial result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}
