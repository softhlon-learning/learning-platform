// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.hexagonal.OutboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@FunctionalInterface
public interface PersistEventLogRepository {

    PersistEventLogResult execute(
          String eventType,
          String customerId,
          String payload);

    sealed interface PersistEventLogResult {
        record EventLogPersisted() implements PersistEventLogResult {}
        record EventLogPersistenceFailed(Throwable cause) implements PersistEventLogResult {}
    }

}
