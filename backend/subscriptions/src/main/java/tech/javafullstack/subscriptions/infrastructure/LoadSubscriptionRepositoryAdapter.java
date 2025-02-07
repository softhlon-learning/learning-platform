// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.javafullstack.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load subscription repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadSubscriptionRepositoryAdapter implements LoadSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadSubscriptionResult execute(
          String subscriptionId) {

        try {
            var entity = subscriptionsJpaRepository
                  .findBySubscriptionId(subscriptionId);

            if (entity.isPresent()) {
                return new SubscriptionLoaded(
                      subscription(entity.get()));
            } else {
                return new SubscriptionNotFound();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new SubscriptionLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Subscription subscription(SubscriptionEntity entity) {

        return new Subscription(
              entity.getId(),
              entity.getSubscriptionId(),
              entity.getCustomerId(),
              entity.isActive(),
              entity.getCanceledAt(),
              entity.getCancelAt(),
              entity.getCancelReason(),
              entity.getPeriodStartAt(),
              entity.getPeriodEndAt(),
              entity.getInvoiceId()
        );

    }

}
