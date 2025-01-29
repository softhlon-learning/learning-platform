// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistCheckoutRepository.PersistCheckoutSessionResult.CheckoutSessionPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class PersistCheckoutRepositoryAdapter implements PersistCheckoutRepository {
    private final CheckoutSessionsJpaRepository checkoutSessionsJpaRepository;

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
              .sessionId(request.sessionId())
              .accountId(request.accountId())
              .build();

    }

}
