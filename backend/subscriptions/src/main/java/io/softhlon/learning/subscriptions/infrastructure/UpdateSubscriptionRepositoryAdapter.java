// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository.Result.InternalFailure;
import io.softhlon.learning.subscriptions.domain.UpdateSubscriptionRepository.Result.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class UpdateSubscriptionRepositoryAdapter implements UpdateSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public Result execute(Subscription subscription) {
        try {
            var entity = subscriptionsRepo.findById(subscription.id()).get();
            updateEntity(subscription, entity);
            subscriptionsRepo.save(entity);
            return new Success();
        } catch (Throwable cause) {
            return new InternalFailure(cause);
        }
    }

    private void updateEntity(Subscription subscription, SubscriptionEntity entity) {
        entity.setStatus(subscription.status());
        entity.setCancelledTime(subscription.cancelledTime());
        entity.setUpdatedTime(subscription.updatedTime());
    }
}
