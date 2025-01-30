// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionService.CreateSubscriptionResult.SubscriptionCreated;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionService.CreateSubscriptionResult.SubscriptionCreationFailed;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.Subscription;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class CreateSubscriptionServiceImpl implements CreateSubscriptionService {
    private final LoadSubscriptionRepository loadSubscriptionRepository;
    private final PersistSubscriptionRepository persistSubscriptionRepository;

    @Override
    public CreateSubscriptionResult execute(
          CreateSubscriptionRequest request) {

        var result = loadSubscriptionRepository.execute(
              new LoadSubscriptionRequest(request.accountId()));

        return switch (result) {
            case SubscriptionLoaded(Subscription subscription) -> persist(request.accountId(), subscription);
            case SubscriptionNotFound() -> persist(request.accountId(), null);
            case SubscriptionLoadFailed(Throwable cause) -> new SubscriptionCreationFailed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    CreateSubscriptionResult persist(
          UUID accountId,
          Subscription subscription) {

        var request = prepareRequest(
              accountId,
              subscription);

        var result = persistSubscriptionRepository.execute(request);

        return switch (result) {
            case SubscriptionPersisted() -> new SubscriptionCreated();
            case SubscriptionPersistenceFailed(Throwable cause) -> new SubscriptionCreationFailed(cause);
        };
    }

    PersistSubscriptionRequest prepareRequest(
          UUID accountId,
          Subscription subscription) {
        if (subscription != null) {
            return new PersistSubscriptionRequest(
                  subscription.id(),
                  subscription.subscriptionId(),
                  subscription.accountId(),
                  true,
                  OffsetDateTime.now(),
                  null
            );
        } else {
            return new PersistSubscriptionRequest(
                  null,
                  null,
                  accountId,
                  true,
                  OffsetDateTime.now(),
                  null
            );
        }
    }

}
