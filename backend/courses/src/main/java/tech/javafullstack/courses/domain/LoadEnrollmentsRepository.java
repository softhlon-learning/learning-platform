// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load enrollments repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadEnrollmentsRepository {

    /**
     * Load all enrollments for given course from repository.
     * @param courseId Course Id
     * @return LoadEnrollmentResult
     */
    ListEnrollmentsResult execute(
          UUID courseId);

    /**
     * Load all enrollments for given course from repository result.
     */
    sealed interface ListEnrollmentsResult {
        record EnrollmentsLoaded(List<Enrollment> enrollments) implements ListEnrollmentsResult {}
        record EnrollmentLoadFailed(Throwable cause) implements ListEnrollmentsResult {}
    }

    record Enrollment(
          UUID courseId,
          UUID accountId,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}

}
