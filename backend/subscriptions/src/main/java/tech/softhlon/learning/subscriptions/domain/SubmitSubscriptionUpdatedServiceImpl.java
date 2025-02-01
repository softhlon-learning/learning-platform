// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

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
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectSubscription;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Succeeded;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.*;

@Slf4j
@Service
class SubmitSubscriptionUpdatedServiceImpl implements SubmitSubscriptionUpdatedService {

    private static final String INCORRECT_SUBSRIPTION = "Non existinng subscription";

    private final String webhookSecret;
    private final LoadSubscriptionRepository loadSubscriptionRepository;
    private final PersistSubscriptionRepository persistSubscriptionRepository;

    public SubmitSubscriptionUpdatedServiceImpl(
          @Value("${stripe.subscription-updated.webhook.secret}")
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
                case "customer.subscription.updated": {

                    var subscriptionId = subscriptionId(event);
                    var customerId = customerId(event);
                    var stripeObject = stripeObject(event);

                    var result = loadSubscriptionRepository.execute(subscriptionId);

                    return switch (result) {
                        case SubscriptionNotFound() -> new IncorrectSubscription(INCORRECT_SUBSRIPTION);
                        case SubscriptionLoaded(Subscription subscription) ->
                              persist(subscriptionId, customerId, stripeObject, subscription);
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
          StripeEventObject stripeObject,
          Subscription subscription) {

        var request = prepareRequest(
              subscriptionId,
              customerId,
              stripeObject,
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
          StripeEventObject stripeObject,
          Subscription subscription) {

        return new PersistSubscriptionRequest(
              subscription.id(),
              subscription.subscriptionId(),
              subscription.customerId(),
              true,
              OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(stripeObject.canceledAt()),
                    ZoneId.systemDefault()),
              OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(stripeObject.cancelAt()),
                    ZoneId.systemDefault()),
              stripeObject.cancelationDetails().feedback()
        );
    }

}
