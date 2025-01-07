// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersistFailed;
import io.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersisted;
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
    public CreateSubscriptionResult execute(CreateSubscriptionRequest request) {
        try {
            var createdEntity = subscriptionsRepo.save(toEntity(request));
            return new SubscriptionPersisted(createdEntity.getId());
        } catch (Throwable cause) {
            return new SubscriptionPersistFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private SubscriptionEntity toEntity(CreateSubscriptionRequest request) {
        return SubscriptionEntity.builder()
              .accountId(request.accountId())
              .status(request.status())
              .startedTime(request.startedTime())
              .build();
    }
}

