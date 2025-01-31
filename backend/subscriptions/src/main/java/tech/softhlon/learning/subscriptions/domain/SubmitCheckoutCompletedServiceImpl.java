// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CreateCustomerService.CreateCustomerResult.CustomerCreated;
import tech.softhlon.learning.subscriptions.domain.CreateCustomerService.CreateCustomerResult.CustomerCreationFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.CheckoutSession;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutNotFoundInDatabase;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitCheckoutCompletedService.Result.Succeeded;

import java.time.OffsetDateTime;

import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.customerId;
import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.sessionId;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class SubmitCheckoutCompletedServiceImpl implements SubmitCheckoutCompletedService {

    private static final String ID = "id";
    private static final String SESSION_NOT_FOUND = "Checkout session not found";

    private final String webhookSecret;
    private final LoadCheckoutRepository loadCheckoutRepository;
    private final PersistCheckoutRepository persistCheckoutRepository;
    private final CreateCustomerService createCustomerService;

    public SubmitCheckoutCompletedServiceImpl(
          @Value("${stripe.checkout-completed.webhook.secret}")
          String webhookSecret,
          LoadCheckoutRepository loadCheckoutRepository,
          PersistCheckoutRepository persistCheckoutRepository,
          CreateCustomerService createCustomerService) {

        this.webhookSecret = webhookSecret;
        this.loadCheckoutRepository = loadCheckoutRepository;
        this.persistCheckoutRepository = persistCheckoutRepository;
        this.createCustomerService = createCustomerService;

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
                    var result = loadCheckoutRepository.execute(
                          sessionId(event));

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
              ));

        return switch (result) {
            case CheckoutSessionPersisted() -> createCustomer(customerId, checkout);
            case CheckoutSessionPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result createCustomer(
          String customerId,
          CheckoutSession checkout) {

        var result = createCustomerService.execute(
              customerId,
              checkout.accountId()
        );

        return switch (result) {
            case CustomerCreated() -> new Succeeded();
            case CustomerCreationFailed(Throwable cause) -> new Failed(cause);
        };

    }

    record DataObject(
          Object object) {}

    record Object(
          String id,
          String customer) {}
}
