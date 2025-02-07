// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.PersistFreeTrialRepository;
import tech.javafullstack.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersisted;
import tech.javafullstack.subscriptions.domain.PersistFreeTrialRepository.PersistFreeTrialResult.FreeTrialPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist free trial repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistFreeTrialRepositoryAdapter implements PersistFreeTrialRepository {
    private final FreeTrialsJpaRepository freeTrialsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistFreeTrialResult execute(
          UUID id,
          UUID accountId,
          OffsetDateTime expireAt) {

        try {

            freeTrialsJpaRepository.save(
                  FreeTrialEntity.builder()
                        .id(id)
                        .accountId(accountId)
                        .expireAt(expireAt)
                        .build()
            );

            return new FreeTrialPersisted();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new FreeTrialPersistenceFailed(cause);
        }

    }

}
