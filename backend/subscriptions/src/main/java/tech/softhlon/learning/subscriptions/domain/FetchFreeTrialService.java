// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Fetch free trial service interface.
 */
@InboundPort
@FunctionalInterface
public interface FetchFreeTrialService {

    /**
     * Fetch user's free trial.
     * @param accountId Account Id
     * @return Result
     */
    Result execute(
          UUID accountId);

    /**
     * Fetch free trial result.
     */
    sealed interface Result {
        record Succeeded(FreeTrial freeTrial) implements Result {}
        record FreeTrialNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record FreeTrial(
          UUID id,
          UUID accountId,
          OffsetDateTime expireAt) {}

}

