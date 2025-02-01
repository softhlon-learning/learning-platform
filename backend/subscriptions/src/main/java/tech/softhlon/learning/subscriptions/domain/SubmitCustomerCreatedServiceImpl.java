// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCustomerRepository.LoadCustomerResult.CustomerNotFound;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCustomersRepository.PersistCustomerResult.CustomerPersistenceFailed;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.IncorrectEventType;
import tech.softhlon.learning.subscriptions.domain.SubmitCustomerCreatedService.Result.Succeeded;

import java.util.UUID;

import static tech.softhlon.learning.subscriptions.domain.StripeEventUtil.customerId;


// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
class SubmitCustomerCreatedServiceImpl implements SubmitCustomerCreatedService {

    private final String webhookSecret;
    private final LoadCustomerRepository loadCustomerRepository;
    private final PersistCustomersRepository persistCustomersRepository;

    public SubmitCustomerCreatedServiceImpl(
          @Value("${stripe.customer-created.webhook.secret}") String webhookSecret,
          LoadCustomerRepository loadCustomerRepository,
          PersistCustomersRepository persistCustomersRepository) {

        this.webhookSecret = webhookSecret;
        this.loadCustomerRepository = loadCustomerRepository;
        this.persistCustomersRepository = persistCustomersRepository;
    }

    @Override
    public Result execute(
          String sigHeader,
          String payload) {

        try {
            var event = Webhook.constructEvent(
                  payload,
                  sigHeader,
                  webhookSecret);

            switch (event.getType()) {
                case "customer.subscription.deleted": {

                    var customerId = customerId(event);
                    var result = loadCustomerRepository.execute(customerId);

                    return switch (result) {
                        case CustomerLoaded(_) -> new Succeeded();
                        case CustomerNotFound() -> persist(customerId, null);
                        case CustomerLoadFailed(Throwable cause) -> new Failed(cause);
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

    private Result persist(
          String customerId,
          UUID accountId) {

        var result = persistCustomersRepository.execute(
              null,
              customerId,
              accountId);

        return switch (result) {
            case CustomerPersisted() -> new Succeeded();
            case CustomerPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
