// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.Failed;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.FreeTrialNotFoundFailed;
import tech.javafullstack.subscriptions.domain.FetchFreeTrialService.Result.Succeeded;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.FreeTrial;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoaded;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialNotFound;

import java.time.Duration;
import java.time.OffsetDateTime;
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

        Duration duration = Duration.between(
              OffsetDateTime.now(),
              freeTrial.expireAt());

        return new FreeTrialInfo(
              duration.isNegative()
                    ? true
                    : false,
              freeTrial.expireAt(),
              timeLeftString(duration)
        );

    }

    private String timeLeftString(Duration duration) {
        if (duration.isNegative()) {
            return null; // Or handle negative durations as needed
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Get remaining minutes after hours

        StringBuilder timeLeft = new StringBuilder();

        // Handle hours
        switch ((int) hours) {
            case 0:
                // Only show hours if non-zero
                break;
            case 1:
                timeLeft.append("1 hour ");
                break;
            default:
                if (hours > 1) {
                    timeLeft.append(hours).append(" hours ");
                }
        }

        // Handle minutes
        switch ((int) minutes) {
            case 0:
                // Only show minutes if non-zero and hours is zero
                if (hours == 0) {
                    timeLeft.append("any moment");
                }
                break;
            case 1:
                timeLeft.append("1 minute");
                break;
            default:
                if (minutes > 1) {
                    timeLeft.append(minutes).append(" minutes");
                }
        }

        return timeLeft.toString().trim();
    }

}
