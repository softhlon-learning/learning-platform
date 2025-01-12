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
public interface LoadEnrollmentRepository {
    LoadEnrollmentResult execute(UUID id);

    record Course(
          UUID courseId,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          String version,
          boolean enrolled) {}

    sealed interface LoadEnrollmentResult {
        record EnrollmentLoaded(Course course) implements LoadEnrollmentResult {}
        record EnrollmentNotFoundInDatabase() implements LoadEnrollmentResult {}
        record EnrollmentLoadFailed(Throwable cause) implements LoadEnrollmentResult {}
    }
}
