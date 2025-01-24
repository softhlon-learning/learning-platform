// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface UpdateEnrollmentRepository {

    UpdateEnrollmentResult execute(
          Enrollment enrollment);

    sealed interface UpdateEnrollmentResult {
        record EnrollmentUpdated(UUID id) implements UpdateEnrollmentResult {}
        record EnrollmentUpdateFailed(Throwable cause) implements UpdateEnrollmentResult {}
    }

    record Enrollment(
          UUID id,
          UUID accountId,
          UUID courseId,
          String status,
          String content,
          OffsetDateTime completedTime) {}

}
