// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface DeleteEnrollmentRepository {
    DeleteEnrollmentResult execute(DeleteEnrollmentRequest request);

    record DeleteEnrollmentRequest(
          UUID courseId,
          UUID accountId) {}

    sealed interface DeleteEnrollmentResult {
        record EnrollmentDeleted() implements DeleteEnrollmentResult {}
        record EnrollementDeletionFailed(Throwable cause) implements DeleteEnrollmentResult {}
    }
}
