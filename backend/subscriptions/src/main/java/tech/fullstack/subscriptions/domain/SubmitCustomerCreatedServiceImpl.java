// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.AccountView;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountNotLoaded;
import tech.fullstack.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoaded;
import tech.fullstack.subscriptions.domain.PersistCustomerRepository.PersistCustomerResult.CustomerPersisted;
import tech.fullstack.subscriptions.domain.PersistCustomerRepository.PersistCustomerResult.CustomerPersistenceFailed;
import tech.fullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.AccountNotFound;
import tech.fullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.Failed;
import tech.fullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.IncorrectEventType;
import tech.fullstack.subscriptions.domain.SubmitCustomerCreatedService.Result.Succeeded;

import java.util.UUID;

import static tech.fullstack.subscriptions.domain.StripeEventUtil.customerId;
import static tech.fullstack.subscriptions.domain.StripeEventUtil.email;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Submit customer created Stripe event service implementation.
 */
@Slf4j
@Service
class SubmitCustomerCreatedServiceImpl implements SubmitCustomerCreatedService {
    private static final String ACCOUNT_NOT_FOUND = "Associated account not found";

    private final String webhookSecret;
    private final LoadCustomerRepository loadCustomerRepository;
    private final PersistCustomerRepository persistCustomerRepository;
    private final LoadAccountByEmailOperator loadAccountByEmailOperator;

    public SubmitCustomerCreatedServiceImpl(
          @Value("${stripe.customer-created.webhook.secret}") String webhookSecret,
          LoadCustomerRepository loadCustomerRepository,
          PersistCustomerRepository persistCustomerRepository,
          LoadAccountByEmailOperator loadAccountByEmailOperator) {

        this.webhookSecret = webhookSecret;
        this.loadCustomerRepository = loadCustomerRepository;
        this.persistCustomerRepository = persistCustomerRepository;
        this.loadAccountByEmailOperator = loadAccountByEmailOperator;

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
                  webhookSecret);

            switch (event.getType()) {
                case "customer.created": {

                    var customerId = customerId(event);
                    var result = loadCustomerRepository.execute(customerId);

                    if (result instanceof CustomerLoaded) {
                        return new Succeeded();
                    }

                    var email = email(event);
                    var loadAccountResult = loadAccountByEmailOperator.execute(email);

                    return switch (loadAccountResult) {
                        case AccountLoaded(AccountView accountView) -> persist(customerId, accountView.id());
                        case AccountNotLoaded() -> new AccountNotFound(ACCOUNT_NOT_FOUND);
                        case AccountLoadFailed(Throwable cause) -> new Failed(cause);
                    };

                }
                default:
                    log.info("service | Event not handled '{}'", event.getType());
                    return new IncorrectEventType("Incorrect event type: " + event.getType());
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result persist(
          String customerId,
          UUID accountId) {

        var result = persistCustomerRepository.execute(
              null,
              customerId,
              accountId
        );

        return switch (result) {
            case CustomerPersisted() -> new Succeeded();
            case CustomerPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
