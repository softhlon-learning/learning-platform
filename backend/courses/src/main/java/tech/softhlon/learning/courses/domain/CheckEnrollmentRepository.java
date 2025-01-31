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
public interface CheckEnrollmentRepository {

    CheckEnrollmentResult execute(
          UUID accountId,
          UUID courseId);

    sealed interface CheckEnrollmentResult {
        record EnrollmentExists() implements CheckEnrollmentResult {}
        record EnrollmentNotFound() implements CheckEnrollmentResult {}
        record CheckEnrollmentFailed(Throwable cause) implements CheckEnrollmentResult {}
    }

}
