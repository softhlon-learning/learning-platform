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
interface LoadEnrollmentsRepository {
    Result execute(UUID id);

    record Enrollment(
          UUID id,
          UUID accountId,
          UUID courseId,
          String status,
          OffsetDateTime enrolledTime,
          OffsetDateTime unenrolledTime,
          OffsetDateTime completedTime) {}

    sealed interface Result {
        record Success(List<Enrollment> enrollment) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
