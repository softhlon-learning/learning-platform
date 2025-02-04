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
 * Load free trial repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadFreeTrialRepository {

    /**
     * Load free trial from repository.
     * @param customerId Customer Id
     * @return LoadCustomerResult
     */
    LoadFreeTrialResult execute(
          UUID accountId);

    /**
     * Load free trial from repository result.
     */
    sealed interface LoadFreeTrialResult {
        record FreeTrialLoaded(FreeTrial freeTrial) implements LoadFreeTrialResult {}
        record FreeTrialNotFound() implements LoadFreeTrialResult {}
        record FreeTrialLoadFailed(Throwable cause) implements LoadFreeTrialResult {}
    }

    record FreeTrial(
          UUID id,
          UUID accountId,
          OffsetDateTime expireAt) {}

}
