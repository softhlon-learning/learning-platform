// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersisted;
import tech.fullstack.subscriptions.domain.PersistSubscriptionRepository.PersistSubscriptionResult.SubscriptionPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist subscription repositry adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistSubscriptionRepositoryAdapter implements PersistSubscriptionRepository {
    private final SubscriptionsJpaRepository subscriptionsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistSubscriptionResult execute(PersistSubscriptionRequest request) {

        try {

            subscriptionsJpaRepository.save(entity(request));
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
              .subscriptionId(request.subscriptionId())
              .customerId(request.customerId())
              .active(request.active())
              .canceledAt(request.canceledAt())
              .cancelAt(request.cancelAt())
              .cancelReason(request.cancelReason())
              .periodStartAt(request.periodStartAt())
              .periodEndAt(request.periodEndAt())
              .invoiceId(request.invoiceId())
              .build();

    }
}
