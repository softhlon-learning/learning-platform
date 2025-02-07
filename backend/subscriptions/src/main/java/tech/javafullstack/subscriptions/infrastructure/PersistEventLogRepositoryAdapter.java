// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.subscriptions.domain.PersistEventLogRepository;
import tech.javafullstack.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersisted;
import tech.javafullstack.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist event log in repository adapter imeplementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class PersistEventLogRepositoryAdapter implements PersistEventLogRepository {
    private final EventLogJpaRepository eventLogJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistEventLogResult execute(
          String eventType,
          String customerId,
          String payload) {

        try {

            eventLogJpaRepository.save(EventLogEntity.builder()
                  .eventType(eventType)
                  .customerId(customerId)
                  .payload(payload)
                  .build()
            );
            return new EventLogPersisted();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EventLogPersistenceFailed(cause);
        }

    }

}
