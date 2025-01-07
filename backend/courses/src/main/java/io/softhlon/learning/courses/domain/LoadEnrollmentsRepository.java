// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadEnrollmentsRepository {
    LoadEnrollmentsResult execute();

    record Enrollment(
          UUID id,
          LoadEnrollmentRepository.Course course,
          String status,
          OffsetDateTime enrolledTime,
          OffsetDateTime unenrolledTime,
          OffsetDateTime completedTime) {}

    record Course(
          String name,
          String description) {}

    sealed interface LoadEnrollmentsResult {
        record Success(List<Enrollment> enrollment) implements LoadEnrollmentsResult {}
        record InternalFailure(Throwable cause) implements LoadEnrollmentsResult {}
    }
}
