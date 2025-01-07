// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

import static io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountRequest;
import static io.softhlon.learning.subscriptions.domain.CheckSubscriptionByAccountRepository.CheckSubscriptionByAccountResult.*;
import static io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionRequest;
import static io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersistFailed;
import static io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersisted;
import static io.softhlon.learning.subscriptions.domain.SubscribeService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SubscribeServiceImpl implements SubscribeService {
    private final CheckSubscriptionByAccountRepository checkSubscriptionRepository;
    private final CreateSubscriptionRepository createSubscriptionRepository;

    @Override
    public Result subscribe(Request request) {
        var exists = checkSubscriptionRepository.execute(new CheckSubscriptionByAccountRequest(request.accountId()));
        return switch (exists) {
            case SubscriptionExists() -> new AccountAlreadySubscribed("Account already subscribed");
            case SubscriptionNotFound() -> persistSubscription(request);
            case CheckSubscriptionFailure(Throwable cause) -> new InternalFailure(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistSubscription(Request request) {
        var result = createSubscriptionRepository.execute(prepareRequest(request));
        return switch (result) {
            case SubscriptionPersisted(UUID id) -> new Success();
            case SubscriptionPersistFailed(Throwable cause) -> new InternalFailure(cause);
        };
    }

    private CreateSubscriptionRequest prepareRequest(Request request) {
        return new CreateSubscriptionRequest(
              request.accountId(),
              SubscriptionStatus.ACTIVE.name(),
              OffsetDateTime.now());
    }
}
