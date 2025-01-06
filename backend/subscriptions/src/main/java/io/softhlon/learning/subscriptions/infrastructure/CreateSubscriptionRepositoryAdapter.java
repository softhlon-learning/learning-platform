// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.Result.InternalFailure;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.Result.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateSubscriptionRepositoryAdapter implements CreateSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public Result execute(Request request) {
        try {
            var createdEntity = subscriptionsRepo.save(toEntity(request));
            return new Success(createdEntity.getId());
        } catch (Throwable cause) {
            return new InternalFailure(cause);
        }
    }

    private SubscriptionEntity toEntity(Request request) {
        return SubscriptionEntity.builder()
              .accountId(request.accountId())
              .status(request.status())
              .startedTime(request.startedTime())
              .build();
    }
}

