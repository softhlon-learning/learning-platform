// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete enrollment repository interface.
 */
@OutboundPort
@DomainRepository
@FunctionalInterface
public interface DeleteEnrollmentRepository {

    /**
     * Delete enrollment in repository.
     * @param courseId  Course Id
     * @param accountId Accout Id
     * @return DeleteEnrollmentResult
     */
    DeleteEnrollmentResult execute(
          UUID courseId,
          UUID accountId);

    /**
     * Delete enrollment in repository result.
     */
    sealed interface DeleteEnrollmentResult {
        record EnrollmentDeleted() implements DeleteEnrollmentResult {}
        record EnrollementDeletionFailed(Throwable cause) implements DeleteEnrollmentResult {}
    }

}
