// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadSubscriptionRepository.LoadSubscriptionResult.SubscriptionNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class LoadSubscriptionRepositoryAdapter implements LoadSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsJpaRepository;

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

    Subscription subscription(SubscriptionEntity entity) {

        return new Subscription(
              entity.getId(),
              entity.getSubscriptionId(),
              entity.getCustomerId(),
              entity.isActive(),
              entity.getActivatedTime(),
              entity.getDeactivatedTime());

    }

}
