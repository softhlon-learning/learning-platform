// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.javafullstack.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;

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
