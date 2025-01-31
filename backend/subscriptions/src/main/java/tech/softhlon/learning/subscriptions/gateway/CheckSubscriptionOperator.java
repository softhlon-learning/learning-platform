// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailed;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator.CheckSusbcriptionResult.CheckSubsriptionFailed;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator.CheckSusbcriptionResult.NotSubscribed;
import tech.softhlon.learning.subscriptions.gateway.CheckSubscriptionOperator.CheckSusbcriptionResult.Subscribed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckSubscriptionOperator {

    private final CheckSubscriptionByAccountRepository checkSubscriptionByAccountRepository;

    public CheckSusbcriptionResult execute(
          CheckSusbcriptionRequest request) {

        var result = checkSubscriptionByAccountRepository.execute(
              request.accountId());

        return switch (result) {
            case SubscriptionExists() -> new Subscribed();
            case SubscriptionNotFound() -> new NotSubscribed();
            case CheckSubscriptionFailed(Throwable cause) -> new CheckSubsriptionFailed(cause);
        };
    }

    public sealed interface CheckSusbcriptionResult {
        record Subscribed() implements CheckSusbcriptionResult {}
        record NotSubscribed() implements CheckSusbcriptionResult {}
        record CheckSubsriptionFailed(Throwable cause) implements CheckSusbcriptionResult {}
    }

    public record CheckSusbcriptionRequest(
          UUID accountId) {}

}
