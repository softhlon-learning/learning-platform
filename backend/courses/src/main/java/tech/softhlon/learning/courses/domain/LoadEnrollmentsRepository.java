// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

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
    ListEnrollmentsResult execute(ListEnrollmentsRequest request);

    sealed interface ListEnrollmentsResult {
        record EnrollmentsLoaded(List<Enrollment> enrollments) implements ListEnrollmentsResult {}
        record EnrollmentLoadFailed(Throwable cause) implements ListEnrollmentsResult {}
    }
    record ListEnrollmentsRequest(UUID courseId) {}

    record Enrollment(
          UUID courseId,
          UUID accountId,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}
}
