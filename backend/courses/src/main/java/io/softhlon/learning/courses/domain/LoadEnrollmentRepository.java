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
public interface LoadEnrollmentRepository {
    LoadEnrollmentResult execute(UUID accountId, UUID courseId);

    sealed interface LoadEnrollmentResult {
        record EnrollmentLoaded(Enrollment course) implements LoadEnrollmentResult {}
        record EnrollmentNotFoundInDatabase() implements LoadEnrollmentResult {}
        record EnrollmentLoadFailed(Throwable cause) implements LoadEnrollmentResult {}
    }
    record Enrollment(
          UUID courseId,
          UUID accountId,
          String status,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}
}
