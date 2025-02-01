// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.Subscription;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionDeletedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Succeeded;

import java.time.OffsetDateTime;

import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.customerId;
import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.subscriptionId;

@Slf4j
@Service
class SubmitSubscriptionDeletedServiceImpl implements SubmitSubscriptionDeletedService {

    private final String webhookSecret;
    private final LoadSubscriptionRepository loadSubscriptionRepository;
    private final PersistSubscriptionRepository persistSubscriptionRepository;

    public SubmitSubscriptionDeletedServiceImpl(
          @Value("${stripe.subscription-deleted.webhook.secret}")
          String webhookSecret,
          LoadSubscriptionRepository loadSubscriptionRepository,
          PersistSubscriptionRepository persistSubscriptionRepository) {

        this.webhookSecret = webhookSecret;
        this.loadSubscriptionRepository = loadSubscriptionRepository;
        this.persistSubscriptionRepository = persistSubscriptionRepository;

    }

    @Override
    public Result execute(
          String sigHeader,
          String payload) {

        try {
            var event = Webhook.constructEvent(
                  payload,
                  sigHeader,
                  webhookSecret);

            switch (event.getType()) {
                case "customer.subscription.deleted": {

                    var subscriptionId = subscriptionId(event);
                    var customerId = customerId(event);

                    var result = loadSubscriptionRepository.execute(subscriptionId);

                    return switch (result) {
                        case SubscriptionNotFound() -> new Failed(null);
                        case SubscriptionLoaded(Subscription subscription) ->
                              persist(subscriptionId, null, subscription);
                        case SubscriptionLoadFailed(Throwable cause) -> new Failed(cause);
                    };

                }
                default:
                    log.info("service | Event not handled '{}'", event.getType());
                    return new IncorrectEventType("Incorrect event type: " + event.getType());
            }

        } catch (Throwable cause) {

            log.error("Erro");
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    Result persist(
          String subscriptionId,
          String customerId,
          Subscription subscription) {

        var request = prepareRequest(
              subscriptionId,
              customerId,
              subscription);

        var result = persistSubscriptionRepository.execute(request);

        return switch (result) {
            case SubscriptionPersisted() -> new Succeeded();
            case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    PersistSubscriptionRequest prepareRequest(
          String subscriptionId,
          String customerId,
          Subscription subscription) {

        return new PersistSubscriptionRequest(
              subscription.id(),
              subscription.subscriptionId(),
              subscription.customerId(),
              false,
              OffsetDateTime.now(),
              null
        );

    }

}
