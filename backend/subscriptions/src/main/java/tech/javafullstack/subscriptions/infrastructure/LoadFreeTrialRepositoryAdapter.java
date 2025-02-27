// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoadFailed;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoaded;
import tech.javafullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialNotFound;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load free trial repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadFreeTrialRepositoryAdapter implements LoadFreeTrialRepository {
    private final FreeTrialsJpaRepository freeTrialsJpaRepository;

    private static final FreeTrial freeTrial =
          new FreeTrial(UUID.randomUUID(), UUID.randomUUID(), OffsetDateTime.now());

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadFreeTrialResult execute(
          UUID accountId) {

        try {
            if (true) {
                return new FreeTrialLoaded(freeTrial);
            }

            var entity = freeTrialsJpaRepository.findByAccountId(accountId);
            if (entity.isPresent()) {
                return new FreeTrialLoaded(customer(entity.get()));
            } else {
                return new FreeTrialNotFound();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new FreeTrialLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private FreeTrial customer(FreeTrialEntity entity) {

        return new FreeTrial(
              entity.getId(),
              entity.getAccountId(),
              entity.getExpireAt()
        );

    }

}
