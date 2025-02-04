// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CreateFreeTrialService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CreateFreeTrialService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create free trial service implementation.
 */
@Service
@RequiredArgsConstructor
class CreateFreeTrialServiceImpl implements CreateFreeTrialService {

    private final PersistFreeTrialRepository persistFreeTrialRepository;

    @Override
    public Result execute(
          UUID accountId,
          OffsetDateTime expireAt) {

        var result = persistFreeTrialRepository
              .execute(
                    null,
                    accountId,
                    expireAt
              );

        return switch (result) {
            case FreeTrialPersisted() -> new Succeeded();
            case FreeTrialPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
