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
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionService.CreateSubscriptionRequest;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionService.CreateSubscriptionResult.SubscriptionCreated;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionService.CreateSubscriptionResult.SubscriptionCreationFailed;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.FinalizeCheckoutService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.CheckoutSession;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutRequest;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutNotFound;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class FinalizeCheckoutServiceImpl implements FinalizeCheckoutService {

    private static final String ID = "id";
    private static final String SESSION_NOT_FOUND = "Checkout session not found";

    private final String webhookSecret;
    private final LoadCheckoutRepository loadCheckoutRepository;
    private final PersistCheckoutRepository persistCheckoutRepository;
    private final CreateSubscriptionService createSubscriptionService;

    public FinalizeCheckoutServiceImpl(
          @Value("${stripe.checkout-result.webhook.secret}")
          String webhookSecret,
          LoadCheckoutRepository loadCheckoutRepository,
          PersistCheckoutRepository persistCheckoutRepository,
          CreateSubscriptionService createSubscriptionService) {

        this.webhookSecret = webhookSecret;
        this.loadCheckoutRepository = loadCheckoutRepository;
        this.persistCheckoutRepository = persistCheckoutRepository;
        this.createSubscriptionService = createSubscriptionService;

    }

    @Override
    public Result execute(Request request) {

        try {
            var event = Webhook.constructEvent(
                  request.payload(),
                  request.sigHeader(),
                  webhookSecret);

            switch (event.getType()) {
                case "checkout.session.completed": {
                    log.info("service | Payment succeeded [{}]", event);
                    var result = loadCheckoutRepository.execute(
                          new LoadCheckoutRequest(sessionId(event)));

                    return switch (result) {
                        case CheckoutLoaded(CheckoutSession session) -> createSubscription(session);
                        case CheckoutNotFound() -> new Result.CheckoutNotFound(SESSION_NOT_FOUND);
                        case CheckoutLoadFailed(Throwable cause) -> new Failed(cause);
                    };
                }
                case "customer.subscription.deleted": {
                    log.info("service | Subscription deleted [{}]", event);
                }
                default:
                    log.info("service | Event not handled [{}]", event);
            }

            return new Succeeded();
        } catch (Throwable cause) {
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String sessionId(Event event) {

        return new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object()
              .id();

    }

    private Result createSubscription(CheckoutSession session) {
        var result = persistCheckoutRepository.execute(
              new PersistCheckoutSessionRequest(
                    session.id(),
                    session.sessionId(),
                    session.accountId(),
                    session.expiredTime(),
                    OffsetDateTime.now()
              ));

        return switch (result) {
            case CheckoutSessionPersisted() -> createSuscription(session.accountId());
            case CheckoutSessionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private Result cancelSubscription(String sessionId) {
        return new Succeeded();
    }

    private Result createSuscription(
          UUID accountId) {

        var result = createSubscriptionService.execute(
              new CreateSubscriptionRequest(accountId));

        return switch (result) {
            case SubscriptionCreated() -> new Succeeded();
            case SubscriptionCreationFailed(Throwable cause) -> new Failed(cause);
        };
    }

    record DataObject(Object object) {}
    record Object(String id) {}
}
