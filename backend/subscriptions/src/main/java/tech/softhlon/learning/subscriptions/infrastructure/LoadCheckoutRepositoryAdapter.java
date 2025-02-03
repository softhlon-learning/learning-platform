// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoadFailed;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutLoaded;
import tech.softhlon.learning.subscriptions.domain.LoadCheckoutRepository.LoadCheckoutResult.CheckoutNotFoundInDatabase;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load checkout repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadCheckoutRepositoryAdapter implements LoadCheckoutRepository {
    private final CheckoutSessionsJpaRepository checkoutSessionsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadCheckoutResult execute(
          String sessionId) {

        try {
            var entity = checkoutSessionsJpaRepository
                  .findBySessionId(sessionId);

            if (entity.isPresent()) {
                return new CheckoutLoaded(
                      checkoutSession(entity.get()));
            } else {
                return new CheckoutNotFoundInDatabase();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckoutLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckoutSession checkoutSession(CheckoutSessionEntity entity) {
        return new CheckoutSession(
              entity.getId(),
              entity.getSessionId(),
              entity.getAccountId(),
              entity.getExpiredAt(),
              entity.getCompletedAt());
    }

}
