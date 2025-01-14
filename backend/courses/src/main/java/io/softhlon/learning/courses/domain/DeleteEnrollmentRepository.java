// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface DeleteEnrollmentRepository {
    DeleteEnrollmentResult execute(DeleteEnrollmentRequest request);

    sealed interface DeleteEnrollmentResult {
        record EnrollmentDeleted() implements DeleteEnrollmentResult {}
        record EnrollementDeletionFailed(Throwable cause) implements DeleteEnrollmentResult {}
    }
    record DeleteEnrollmentRequest(
          UUID courseId,
          UUID accountId) {}
}
