// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountRequest;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.CheckSubscriptionFailed;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionExists;
import tech.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.AccountAlreadySubscribedFailed;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubscribeService.Result.Succeeded;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class SubscribeServiceImpl implements SubscribeService {

    private final CheckSubscriptionByAccountRepository checkSubscriptionRepository;
    private final CreateSubscriptionRepository createSubscriptionRepository;

    @Override
    public Result execute(
          Request request) {

        try {
            var exists = checkSubscriptionRepository.execute(
                  new CheckSubscriptionByAccountRequest(request.accountId()));

            return switch (exists) {
                case SubscriptionExists() -> new AccountAlreadySubscribedFailed("Account already subscribed");
                case SubscriptionNotFound() -> persistSubscription(request);
                case CheckSubscriptionFailed(Throwable cause) -> new Failed(cause);
            };

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new Failed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistSubscription(
          Request request) {

        var result = createSubscriptionRepository.execute(
              prepareRequest(request));

        return switch (result) {
            case SubscriptionPersisted(UUID id) -> new Succeeded();
            case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private CreateSubscriptionRequest prepareRequest(
          Request request) {

        return new CreateSubscriptionRequest(
              request.accountId(),
              SubscriptionStatus.ACTIVE.name(),
              OffsetDateTime.now());

    }

}
