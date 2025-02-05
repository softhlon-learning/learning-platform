// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist free trial repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistFreeTrialRepository {

    /**
     * Persist free trial in repository.
     * @param id        Free trial Id
     * @param accountId Account Id
     * @param expireAt  Free trial expiration time
     * @return
     */
    PersistFreeTrialResult execute(
          UUID id,
          UUID accountId,
          OffsetDateTime expireAt);

    /**
     * Persist free trial in repository result.
     */
    sealed interface PersistFreeTrialResult {
        record FreeTrialPersisted() implements PersistFreeTrialResult {}
        record FreeTrialPersistenceFailed(Throwable cause) implements PersistFreeTrialResult {}
    }

}
