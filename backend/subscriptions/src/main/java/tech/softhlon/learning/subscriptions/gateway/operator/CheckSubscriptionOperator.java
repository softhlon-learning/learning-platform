// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway.operator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailed;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.FreeTrial;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialNotFound;
import tech.softhlon.learning.subscriptions.gateway.operator.CheckSubscriptionOperator.CheckSusbcriptionResult.CheckSubsriptionFailed;
import tech.softhlon.learning.subscriptions.gateway.operator.CheckSubscriptionOperator.CheckSusbcriptionResult.ActiveFreeTrial;
import tech.softhlon.learning.subscriptions.gateway.operator.CheckSubscriptionOperator.CheckSusbcriptionResult.NotSubscribed;
import tech.softhlon.learning.subscriptions.gateway.operator.CheckSubscriptionOperator.CheckSusbcriptionResult.Subscribed;

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
public class CheckSubscriptionOperator {

    private final CheckSubscriptionByAccountRepository checkSubscriptionByAccountRepository;
    private final LoadFreeTrialRepository loadFreeTrialRepository;

    /**
     * Check if account is subscribed.
     * @param request CheckSusbcriptionRequest
     * @return CheckSusbcriptionResult
     */
    public CheckSusbcriptionResult execute(CheckSusbcriptionRequest request) {

        var result = checkSubscriptionByAccountRepository.execute(request.accountId());
        return switch (result) {
            case SubscriptionExists() -> new Subscribed();
            case SubscriptionNotFound() -> checkFreeTrial(request.accountId());
            case CheckSubscriptionFailed(Throwable cause) -> new CheckSubsriptionFailed(cause);
        };

    }

    public sealed interface CheckSusbcriptionResult {
        record Subscribed() implements CheckSusbcriptionResult {}
        record NotSubscribed() implements CheckSusbcriptionResult {}
        record ActiveFreeTrial() implements CheckSusbcriptionResult {}
        record CheckSubsriptionFailed(Throwable cause) implements CheckSusbcriptionResult {}
    }

    public record CheckSusbcriptionRequest(
          UUID accountId) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckSusbcriptionResult checkFreeTrial(UUID accountId) {

        var result = loadFreeTrialRepository.execute(accountId);
        return switch (result) {
            case FreeTrialLoaded(FreeTrial freeTrial) when isActive(freeTrial)-> new ActiveFreeTrial();
            case FreeTrialLoaded(FreeTrial freeTrial) when !isActive(freeTrial)-> new NotSubscribed();
            case FreeTrialNotFound(), FreeTrialLoadFailed(_) -> new NotSubscribed();
            default -> throw new IllegalStateException("Unexpected value: " + result);
        };

    }

    private boolean isActive (FreeTrial freeTrial) {
        return freeTrial.expireAt().isAfter(OffsetDateTime.now());
    }

}
