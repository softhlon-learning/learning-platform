// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Fetch free trial info service interface.
 */
@InboundPort
@FunctionalInterface
public interface FetchFreeTrialService {

    /**
     * Fetch user's free trial info.
     * @param accountId Account Id
     * @return Result
     */
    Result execute(
          UUID accountId);

    /**
     * Fetch free trial info result.
     */
    sealed interface Result {
        record Succeeded(FreeTrialInfo freeTrial) implements Result {}
        record FreeTrialNotFoundFailed() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record FreeTrialInfo(
          boolean expired,
          OffsetDateTime expireAt,
          String timeLeft) {}

}

