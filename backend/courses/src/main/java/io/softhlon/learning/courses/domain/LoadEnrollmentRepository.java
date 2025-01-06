// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadEnrollmentRepository {
    Result execute(UUID id);

    record Enrollment(
          UUID id,
          Course course,
          String status,
          OffsetDateTime enrolledTime,
          OffsetDateTime unenrolledTime,
          OffsetDateTime completedTime,
          OffsetDateTime createdTime,
          OffsetDateTime updatedTime) {}

    record Course(
          String name,
          String description) {}

    sealed interface Result {
        record Success(Enrollment enrollment) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
