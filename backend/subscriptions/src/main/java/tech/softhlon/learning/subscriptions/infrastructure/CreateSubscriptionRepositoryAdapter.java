// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.CreateSubscriptionRepository.CreateSubscriptionResult.SubscriptionPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateSubscriptionRepositoryAdapter implements CreateSubscriptionRepository {

    private final SubscriptionsJpaRepository subscriptionsRepo;

    @Override
    public CreateSubscriptionResult execute(
          CreateSubscriptionRequest request) {

        try {
            var createdEntity = subscriptionsRepo.save(
                  toEntity(request));

            return new SubscriptionPersisted(
                  createdEntity.getId());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new SubscriptionPersistenceFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private SubscriptionEntity toEntity(
          CreateSubscriptionRequest request) {

        return SubscriptionEntity.builder()
              .accountId(request.accountId())
              .active(true)
              .activatedTime(request.startedTime())
              .build();

    }

}
