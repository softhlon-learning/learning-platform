// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class PersistSubscriptionRepositoryAdapter implements PersistSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsJpaRepository;

    @Override
    public PersistSubscriptionResult execute(PersistSubscriptionRequest request) {

        try {
            subscriptionsJpaRepository.save(
                  entity(request));

            return new SubscriptionPersisted();

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new SubscriptionPersistenceFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private SubscriptionEntity entity(
          PersistSubscriptionRequest request) {

        return SubscriptionEntity.builder()
              .id(request.id())
              .accountId(request.accountId())
              .active(request.active())
              .activatedTime(request.activatedTime())
              .deactivatedTime(request.deactivatedTime())
              .build();

    }
}
