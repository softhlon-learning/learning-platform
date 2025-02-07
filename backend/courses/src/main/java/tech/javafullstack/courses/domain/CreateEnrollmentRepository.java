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
 * Create enrollment repository implementation.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreateEnrollmentRepository {

    /**
     * Create enrollment in repository.
     * @param courseId   Course Id
     * @param accountId  Account Id
     * @param enrolledAt Enrollment time
     * @return CreateEnrollmentResult
     */
    CreateEnrollmentResult execute(
          UUID courseId,
          UUID accountId,
          OffsetDateTime enrolledAt);

    /**
     * Create enrollment in repository result.
     */
    sealed interface CreateEnrollmentResult {
        record EnrollmentPersisted(UUID uuid) implements CreateEnrollmentResult {}
        record EnrollementPersistenceFailed(Throwable cause) implements CreateEnrollmentResult {}
    }

}
