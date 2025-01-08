// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository.UpdateSubscriptionResult.SubscriptionPersisted;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository.UpdateSubscriptionResult.SubscriptionPersistenceFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class UpdateSubscriptionRepositoryAdapter implements UpdateSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public UpdateSubscriptionResult execute(Subscription subscription) {
        try {
            var entity = subscriptionsRepo.findById(subscription.id()).get();
            updateEntity(subscription, entity);
            subscriptionsRepo.save(entity);
            return new SubscriptionPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new SubscriptionPersistenceFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(Subscription subscription, SubscriptionEntity entity) {
        entity.setStatus(subscription.status());
        entity.setCancelledTime(subscription.cancelledTime());
    }
}
