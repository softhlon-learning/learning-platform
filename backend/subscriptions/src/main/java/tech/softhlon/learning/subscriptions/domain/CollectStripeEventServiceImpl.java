// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import com.google.gson.Gson;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Failed;
import tech.softhlon.learning.subscriptions.domain.CollectStripeEventService.Result.Succeeded;
import tech.softhlon.learning.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * CollectStripeEventService implementation.
 */
@Slf4j
@Service
class CollectStripeEventServiceImpl implements CollectStripeEventService {
    private final String webhookSecret;
    private final PersistEventLogRepository persistEventLogRepository;

    public CollectStripeEventServiceImpl(
          @Value("${stripe.generic-event.webhook.secret}")
          String webhookSecret,
          PersistEventLogRepository persistEventLogRepository) {

        this.persistEventLogRepository = persistEventLogRepository;
        this.webhookSecret = webhookSecret;
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

            var customerId = customerId(event);

            var result = persistEventLogRepository.execute(
                  event.getType(),
                  customerId,
                  payload);

            return switch (result) {
                case EventLogPersisted() -> new Succeeded();
                case EventLogPersistenceFailed(Throwable cause) -> new Failed(cause);
            };
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private String customerId(Event event) {

        var object = new Gson()
              .fromJson(
                    event.getData().toJson(),
                    DataObject.class)
              .object();

        return object.customer() != null
              ? object.customer()
              : object.id();

    }

    private record DataObject(
          Object object) {}

    private record Object(
          String id,
          String customer) {}
}
