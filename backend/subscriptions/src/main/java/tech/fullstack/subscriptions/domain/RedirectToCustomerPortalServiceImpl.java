// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.billingportal.Session;
import com.stripe.param.billingportal.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.LoadCustomerByAccountRepository.Customer;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Failed;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.Succeeded;
import tech.fullstack.subscriptions.domain.RedirectToCustomerPortalService.Result.UnknownCustomer;

import java.util.UUID;

import static tech.fullstack.subscriptions.domain.LoadCustomerByAccountRepository.LoadCustomerResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Redirect to Stripe customer portal service implementation.
 */
@Slf4j
@Service
class RedirectToCustomerPortalServiceImpl implements RedirectToCustomerPortalService {
    private static final String CUSTOMER_NOT_FOUND = "Customer not found";
    private final String serviceBaseUrl;
    private final LoadCustomerByAccountRepository loadCustomerByAccountRepository;

    public RedirectToCustomerPortalServiceImpl(
          @Value("${service.base-url}") String serviceBaseUrl,
          @Value("${stripe.api-key}") String stripeApiKey,
          LoadCustomerByAccountRepository loadCustomerByAccountRepository) {

        Stripe.apiKey = stripeApiKey;
        this.serviceBaseUrl = serviceBaseUrl;
        this.loadCustomerByAccountRepository = loadCustomerByAccountRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          UUID accountId) {

        try {
            var result = loadCustomerByAccountRepository.execute(accountId);

            return switch (result) {
                case CustomerLoaded(Customer customer) -> customerPortal(customer.customerId());
                case CustomerNotFound() -> new UnknownCustomer(CUSTOMER_NOT_FOUND);
                case CustomerLoadFailed(Throwable cause) -> new Failed(cause);
            };
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result customerPortal(
          String cusomerId) throws StripeException {

        var sessionCreateParams =
              new SessionCreateParams.Builder()
                    .setReturnUrl(serviceBaseUrl)
                    .setCustomer(cusomerId)
                    .build();

        var session = Session.create(
              sessionCreateParams);

        return new Succeeded(
              session.getUrl());

    }

}
