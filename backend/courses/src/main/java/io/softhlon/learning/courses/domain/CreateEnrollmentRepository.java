// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.domain.DomainRepository;
import io.softhlon.learning.common.hexagonal.OutboundPort;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface CreateEnrollmentRepository {
    CreateEnrollmentResult execute(CreateEnrollmentRequest request);

    sealed interface CreateEnrollmentResult {
        record EnrollmentPersisted(UUID uuid) implements CreateEnrollmentResult {}
        record EnrollementPersistenceFailed(Throwable cause) implements CreateEnrollmentResult {}
    }
    record CreateEnrollmentRequest(
          UUID courseId,
          UUID accountId,
          String status,
          OffsetDateTime enrolledTime) {}
}
