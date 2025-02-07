// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.fullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.fullstack.subscriptions.domain.LoadFreeTrialRepository;
import tech.fullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoadFailed;
import tech.fullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialLoaded;
import tech.fullstack.subscriptions.domain.LoadFreeTrialRepository.LoadFreeTrialResult.FreeTrialNotFound;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadFreeTrialResult execute(
          UUID accountId) {

        try {
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
