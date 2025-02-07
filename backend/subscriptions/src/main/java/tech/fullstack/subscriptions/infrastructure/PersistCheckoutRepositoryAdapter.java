// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.PersistCheckoutRepository;
import tech.fullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.fullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist checkout session repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistCheckoutRepositoryAdapter implements PersistCheckoutRepository {
    private final CheckoutSessionsJpaRepository checkoutSessionsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistCheckoutSessionResult execute(
          PersistCheckoutSessionRequest request) {

        try {
            checkoutSessionsJpaRepository.save(
                  entity(request));

            return new CheckoutSessionPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckoutSessionPersistenceFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckoutSessionEntity entity(
          PersistCheckoutSessionRequest request) {

        return CheckoutSessionEntity.builder()
              .id(request.id())
              .sessionId(request.sessionId())
              .accountId(request.accountId())
              .expiredAt(request.expiredTime())
              .completedAt(request.completedTime())
              .build();

    }

}
