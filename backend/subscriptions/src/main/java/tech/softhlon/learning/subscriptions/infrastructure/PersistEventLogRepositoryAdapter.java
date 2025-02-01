// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.subscriptions.domain.PersistEventLogRepository;
import tech.softhlon.learning.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersisted;
import tech.softhlon.learning.subscriptions.domain.PersistEventLogRepository.PersistEventLogResult.EventLogPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class PersistEventLogRepositoryAdapter implements PersistEventLogRepository {
    private final EventLogJpaRepository eventLogJpaRepository;

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
                  .build());
            return new EventLogPersisted();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EventLogPersistenceFailed(cause);
        }

    }

}
