// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.operator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersistenceFailed;
import tech.softhlon.learning.subscriptions.gateway.operator.CreateFreeTrialOperator.CreateFreeTrialResult.FreeTrialCreated;
import tech.softhlon.learning.subscriptions.gateway.operator.CreateFreeTrialOperator.CreateFreeTrialResult.FreeTrialCreationFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Check subscriptions operator.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateFreeTrialOperator {

    private final PersistFreeTrialRepository persistFreeTrialRepository;

    /**
     * Create free trial.
     * @param request CheckSusbcriptionRequest
     * @return CheckSusbcriptionResult
     */
    public CreateFreeTrialResult execute(
          UUID accountId,
          OffsetDateTime expireAt) {

        var result = persistFreeTrialRepository.execute(
              null,
              accountId,
              expireAt
        );

        return switch (result) {
            case FreeTrialPersisted() -> new FreeTrialCreated();
            case FreeTrialPersistenceFailed(Throwable cause) -> new FreeTrialCreationFailed(cause);
        };

    }

    public sealed interface CreateFreeTrialResult {
        record FreeTrialCreated() implements CreateFreeTrialResult {}
        record FreeTrialCreationFailed(Throwable cause) implements CreateFreeTrialResult {}
    }

}
