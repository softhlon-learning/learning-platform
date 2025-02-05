// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist enrollment repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface PersistEnrollmentRepository {

    /**
     * Persist enrollment in repository.
     * @param request Operation request
     * @return PersistEnrollmentResult
     */
    PersistEnrollmentResult execute(
          PersistEnrollmentRequest request);

    /**
     * Persist enrollment in repository result.
     */
    sealed interface PersistEnrollmentResult {
        record EnrollmentPersisted() implements PersistEnrollmentResult {}
        record EnrollmentNotPresentFoundFailed() implements PersistEnrollmentResult {}
        record EnrollmentPersistenceFailed(Throwable cause) implements PersistEnrollmentResult {}
    }

    record PersistEnrollmentRequest(
          UUID courseId,
          UUID accountId,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}

}
