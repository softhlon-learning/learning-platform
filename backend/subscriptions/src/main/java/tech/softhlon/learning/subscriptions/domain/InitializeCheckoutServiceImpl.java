// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.InitializeCheckoutService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.Customer;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerRequest;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerLoadLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.CustomerNotFound;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionRequest;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class InitializeCheckoutServiceImpl implements InitializeCheckoutService {

    private static final String HOME_PATH = "/home";
    private static final String SUBSCRIBE_PATH = "/subscribe";

    private final String serviceBaseUrl;
    private final PersistCheckoutRepository persistCheckoutRepository;
    private final LoadCustomerByAccountRepository loadCustomerByAccountRepository;

    public InitializeCheckoutServiceImpl(
          @Value("${stripe.api-key}") String stripeApiKey,
          @Value("${service.base-url}") String serviceBaseUrl,
          PersistCheckoutRepository persistCheckoutRepository,
          LoadCustomerByAccountRepository loadCustomerByAccountRepository) {

        Stripe.apiKey = stripeApiKey;
        this.serviceBaseUrl = serviceBaseUrl;
        this.persistCheckoutRepository = persistCheckoutRepository;
        this.loadCustomerByAccountRepository = loadCustomerByAccountRepository;

    }

    @Override
    public Result execute(
          Request request) {

        try {
            SessionCreateParams params = new SessionCreateParams.Builder()
                  .setCustomer(customerId(request.acccountId()))
                  .setCustomerEmail(request.email())
                  .setSuccessUrl(serviceBaseUrl + HOME_PATH)
                  .setCancelUrl(serviceBaseUrl + SUBSCRIBE_PATH)
                  .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                  .addLineItem(new SessionCreateParams.LineItem.Builder()
                        .setQuantity(1L)
                        .setPrice(request.priceId())
                        .build())
                  .build();

            var session = Session.create(params);

            var result = saveSession(
                  request.acccountId(),
                  session.getId());

            return switch (result) {
                case CheckoutSessionPersisted() -> new Succeeded(session.getUrl());
                case CheckoutSessionPersistenceFailed(Throwable cause) -> new Failed(cause);
            };

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new Failed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String customerId(
          UUID accountId) {

        var result = loadCustomerByAccountRepository.execute(new
              LoadCustomerRequest(accountId));

        return switch (result) {
            case CustomerNotFound(), CustomerLoadFailed(_) -> null;
            case CustomerLoadLoaded(Customer customer) -> customer.customerId();
        };

    }

    private PersistCheckoutSessionResult saveSession(
          UUID accountId,
          String sessionId) {

        return persistCheckoutRepository.execute(
              new PersistCheckoutSessionRequest(
                    null,
                    sessionId,
                    accountId,
                    null,
                    null));

    }

}
