// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.FreeTrialNotFoundFailed;
import tech.softhlon.learning.subscriptions.domain.FetchFreeTrialService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.FreeTrial;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialNotFound;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Fetch profile service implementation.
 */
@Service
@RequiredArgsConstructor
class FetchFreeTrialServiceImpl implements FetchFreeTrialService {

    private final LoadFreeTrialRepository loadFreeTrialRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId) {

        var result = loadFreeTrialRepository.execute(accountId);
        return switch (result) {
            case FreeTrialLoaded(FreeTrial freeTrial) -> new Succeeded(freeTrial(freeTrial));
            case FreeTrialNotFound() -> new FreeTrialNotFoundFailed();
            case FreeTrialLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private FreeTrialInfo freeTrial(
          FreeTrial freeTrial) {

        return new FreeTrialInfo(
              false,
              freeTrial.expireAt(),
              null
        );

    }

}
