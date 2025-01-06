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
interface UpdateEnrollmentRepository {
    Result execute(Enrollment enrollment);

    record Enrollment(
          UUID id,
          UUID accountId,
          UUID courseId,
          String status,
          OffsetDateTime enrolledTime,
          OffsetDateTime unenrolledTime,
          OffsetDateTime completedTime) {}

    sealed interface Result {
        record Success(UUID id) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
