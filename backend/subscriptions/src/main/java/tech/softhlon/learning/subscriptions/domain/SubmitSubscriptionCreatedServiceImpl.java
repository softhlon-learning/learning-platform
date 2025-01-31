// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.google.gson.Gson;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.Subscription;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionCreatedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionCreatedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitSubscriptionCreatedService.Result.Succeeded;

import java.time.OffsetDateTime;

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

    @Override
    public Result execute(Request request) {

        try {
            var event = Webhook.constructEvent(
                  request.payload(),
                  request.sigHeader(),
                  webhookSecret);

            switch (event.getType()) {
                case "customer.subscription.created": {
                    log.info("service | Event received '{}'", event.getType());

                    var subscriptionId = subscriptionId(event);
                    var customerId = customerId(event);

                    var result = loadSubscriptionRepository.execute(
                          new LoadSubscriptionRequest(subscriptionId));

                    return switch (result) {
                        case SubscriptionNotFound() -> persist(subscriptionId, customerId,  null);
                        case SubscriptionLoaded(Subscription subscription) -> persist(subscriptionId, null, subscription);
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

    private String subscriptionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    private String customerId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .customer();

    }

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
        if (subscription != null) {
            return new PersistSubscriptionRequest(
                  subscription.id(),
                  subscription.subscriptionId(),
                  subscription.customerId(),
                  subscription.accountId(),
                  true,
                  OffsetDateTime.now(),
                  null
            );
        } else {
            return new PersistSubscriptionRequest(
                  null,
                  subscriptionId,
                  customerId,
                  null,
                  true,
                  OffsetDateTime.now(),
                  null
            );
        }
    }

    record DataObject(
          Object object) {}

    record Object(
          String id,
          String customer) {}

}
