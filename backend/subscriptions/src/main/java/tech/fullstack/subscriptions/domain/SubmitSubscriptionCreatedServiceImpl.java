// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionCreatedService.Result.Failed;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionCreatedService.Result.IncorrectEventType;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionCreatedService.Result.Succeeded;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static tech.fullstack.subscriptions.domain.StripeEventUtil.StripeEventObject;
import static tech.fullstack.subscriptions.domain.StripeEventUtil.stripeObject;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit created subscription Stripe event service implementation.
 */
@Slf4j
@Service
class SubmitSubscriptionCreatedServiceImpl implements SubmitSubscriptionCreatedService {
    private final String webhookSecret;
    private final LoadSubscriptionRepository loadSubscriptionRepository;
    private final PersistSubscriptionRepository persistSubscriptionRepository;

    public SubmitSubscriptionCreatedServiceImpl(
          @Value("${stripe.subscription-created.webhook.secret}")
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
                case "customer.subscription.created": {

                    var stripeObject = stripeObject(event);

                    var request = prepareRequest(
                          stripeObject);

                    var result = persistSubscriptionRepository.execute(request);
                    return switch (result) {
                        case SubscriptionPersisted() -> new Succeeded();
                        case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
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
          StripeEventObject stripeEventObject) {

        var request = prepareRequest(
              stripeEventObject);

        var result = persistSubscriptionRepository.execute(request);

        return switch (result) {
            case SubscriptionPersisted() -> new Succeeded();
            case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private PersistSubscriptionRequest prepareRequest(
          StripeEventObject stripeEventObject) {
        return new PersistSubscriptionRequest(
              null,
              stripeEventObject.id(),
              stripeEventObject.customer(),
              false,
              null,
              null,
              null,
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
