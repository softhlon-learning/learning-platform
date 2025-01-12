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
public interface CheckEnrollmentRepository {
    CheckEnrollmentResult execute(CheckEnrollmentRequest request);

    sealed interface CheckEnrollmentResult {
        record EnrollmentExists() implements CheckEnrollmentResult {}
        record EnrollmentNotFound() implements CheckEnrollmentResult {}
        record CheckEnrollmentFailed(Throwable cause) implements CheckEnrollmentResult {}
    }
    record CheckEnrollmentRequest(UUID accountId, UUID courseId) {}
}
