// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadCheckoutRepository.CheckoutSession;
import tech.javafullstack.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoaded;
import tech.javafullstack.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutNotFoundInDatabase;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionRequest;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;
import tech.javafullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.Failed;
import tech.javafullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.IncorrectEventType;
import tech.javafullstack.subscriptions.domain.SubmitCheckoutCompletedService.Result.Succeeded;

import java.time.OffsetDateTime;

import static tech.javafullstack.subscriptions.domain.StripeEventUtil.customerId;
import static tech.javafullstack.subscriptions.domain.StripeEventUtil.sessionId;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit checkout session completed Stripe event service implementation.
 */
@Slf4j
@Service
class SubmitCheckoutCompletedServiceImpl implements SubmitCheckoutCompletedService {

    private static final String ID = "id";
    private static final String SESSION_NOT_FOUND = "Checkout session not found";

    private final String webhookSecret;
    private final LoadCheckoutRepository loadCheckoutRepository;
    private final PersistCheckoutRepository persistCheckoutRepository;

    public SubmitCheckoutCompletedServiceImpl(
          @Value("${stripe.checkout-completed.webhook.secret}")
          String webhookSecret,
          LoadCheckoutRepository loadCheckoutRepository,
          PersistCheckoutRepository persistCheckoutRepository) {

        this.webhookSecret = webhookSecret;
        this.loadCheckoutRepository = loadCheckoutRepository;
        this.persistCheckoutRepository = persistCheckoutRepository;

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
                case "checkout.session.completed": {
                    var result = loadCheckoutRepository.execute(sessionId(event));
                    return switch (result) {
                        case CheckoutLoaded(CheckoutSession checkout) -> persistCheckout(customerId(event), checkout);
                        case CheckoutNotFoundInDatabase() -> new Result.CheckoutNotFound(SESSION_NOT_FOUND);
                        case CheckoutLoadFailed(Throwable cause) -> new Failed(cause);
                    };
                }
                default:
                    log.info("service | Event not handled '{}'", event.getType());
                    return new IncorrectEventType("Incorrect event type: " + event.getType());
            }
        } catch (Throwable cause) {
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persistCheckout(
          String customerId,
          CheckoutSession checkout) {

        var result = persistCheckoutRepository.execute(
              new PersistCheckoutSessionRequest(
                    checkout.id(),
                    checkout.sessionId(),
                    checkout.accountId(),
                    checkout.expiredTime(),
                    OffsetDateTime.now()
              )
        );

        return switch (result) {
            case CheckoutSessionPersisted() -> new Succeeded();
            case CheckoutSessionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
