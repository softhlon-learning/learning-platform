// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load enrollment repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadEnrollmentRepository {

    /**
     * Load enrollment from repository.
     * @param accountId Account Id
     * @param courseId  Course Id
     * @return LoadEnrollmentResult
     */
    LoadEnrollmentResult execute(
          UUID accountId,
          UUID courseId);

    /**
     * Load enrollment from repository result.
     */
    sealed interface LoadEnrollmentResult {
        record EnrollmentLoaded(Enrollment course) implements LoadEnrollmentResult {}
        record EnrollmentNotFoundInDatabase() implements LoadEnrollmentResult {}
        record EnrollmentLoadFailed(Throwable cause) implements LoadEnrollmentResult {}
    }

    record Enrollment(
          UUID id,
          UUID courseId,
          UUID accountId,
          String content,
          OffsetDateTime enrolledTime,
          OffsetDateTime completedTime) {}

}
