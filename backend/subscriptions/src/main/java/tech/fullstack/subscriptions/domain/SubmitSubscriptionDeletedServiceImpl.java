// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.fullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.fullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;
import tech.fullstack.subscriptions.domain.LoadSubscriptionRepository.Subscription;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Failed;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.IncorrectEventType;
import tech.fullstack.subscriptions.domain.SubmitSubscriptionDeletedService.Result.Succeeded;

import java.time.OffsetDateTime;

import static tech.fullstack.subscriptions.domain.StripeEventUtil.customerId;
import static tech.fullstack.subscriptions.domain.StripeEventUtil.subscriptionId;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit deleted subscription Stripe event service iomplementation.
 */
@Slf4j
@Service
class SubmitSubscriptionDeletedServiceImpl implements SubmitSubscriptionDeletedService {
    private static final String MANUAL_DELETE_REASON = "manual_delete";
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
                case "customer.subscription.deleted": {

                    var subscriptionId = subscriptionId(event);
                    var customerId = customerId(event);
                    var result = loadSubscriptionRepository.execute(subscriptionId);

                    return switch (result) {
                        case SubscriptionNotFound() -> new Failed(null);
                        case SubscriptionLoaded(Subscription subscription) -> persist(subscriptionId, subscription);
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
          String subscriptionId,
          Subscription subscription) {

        var request = prepareRequest(
              subscriptionId,
              subscription);

        var result = persistSubscriptionRepository.execute(request);

        return switch (result) {
            case SubscriptionPersisted() -> new Succeeded();
            case SubscriptionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private PersistSubscriptionRequest prepareRequest(
          String subscriptionId,
          Subscription subscription) {

        return new PersistSubscriptionRequest(
              subscription.id(),
              subscription.subscriptionId(),
              subscription.customerId(),
              false,
              OffsetDateTime.now(),
              OffsetDateTime.now(),
              MANUAL_DELETE_REASON,
              subscription.periodStartAt(),
              subscription.periodEndAt(),
              null
        );

    }

}
