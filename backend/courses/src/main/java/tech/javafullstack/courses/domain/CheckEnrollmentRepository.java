// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.domain.DomainRepository;
import tech.javafullstack.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Check enrollment repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CheckEnrollmentRepository {

    /**
     * Check if given enrollment exist in repository.
     * @param accountId Account Id
     * @param courseId  Course Id
     * @return
     */
    CheckEnrollmentResult execute(
          UUID accountId,
          UUID courseId);

    /**
     * Check if given enrollment exist in repository result.
     */
    sealed interface CheckEnrollmentResult {
        record EnrollmentExists() implements CheckEnrollmentResult {}
        record EnrollmentNotFound() implements CheckEnrollmentResult {}
        record CheckEnrollmentFailed(Throwable cause) implements CheckEnrollmentResult {}
    }

}
