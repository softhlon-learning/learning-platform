// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.Subscription;
import tech.javafullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.javafullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.javafullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Failed;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectEventType;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.IncorrectSubscription;
import tech.javafullstack.subscriptions.domain.SubmitSubscriptionUpdatedService.Result.Succeeded;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static tech.javafullstack.subscriptions.domain.StripeEventUtil.StripeEventObject;
import static tech.javafullstack.subscriptions.domain.StripeEventUtil.stripeObject;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit updated subscription Stripe event service implementation.
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String payload,
          String sigHeader) {

        try {
            var event = Webhook.constructEvent(
                  payload,
                  sigHeader,
                  webhookSecret
            );
            switch (event.getType()) {
                case "customer.subscription.updated": {

                    var stripeObject = stripeObject(event);
                    var result = loadSubscriptionRepository.execute(stripeObject.id());

                    return switch (result) {
                        case SubscriptionNotFound() -> new IncorrectSubscription(INCORRECT_SUBSRIPTION);
                        case SubscriptionLoaded(Subscription subscription) -> persist(stripeObject, subscription);
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

    private Result persist(
          StripeEventObject stripeObject,
          Subscription subscription) {

        var request = prepareRequest(
              stripeObject,
              subscription
        );

        var result = persistSubscriptionRepository.execute(request);

        return switch (result) {
            case SubscriptionPersisted() -> new Succeeded();
            case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private PersistSubscriptionRequest prepareRequest(
          StripeEventObject stripeEventObject,
          Subscription subscription) {

        return new PersistSubscriptionRequest(
              subscription.id(),
              subscription.subscriptionId(),
              subscription.customerId(),
              subscription.active(),
              offsetDateTime(stripeEventObject.canceledAt()),
              offsetDateTime(stripeEventObject.cancelAt()),
              stripeEventObject.cancelationDetails() != null
                    ? stripeEventObject.cancelationDetails().feedback()
                    : null,
              offsetDateTime(stripeEventObject.periodStartAt()),
              offsetDateTime(stripeEventObject.periodEndAt()),
              stripeEventObject.invoiceId()
        );
    }

    private OffsetDateTime offsetDateTime(String time) {

        if (time == null)
            return null;

        var instant = Instant.ofEpochMilli(
              Long.parseLong(time) * 1000);

        return OffsetDateTime.ofInstant(
              instant,
              ZoneId.systemDefault()
        );

    }

}
