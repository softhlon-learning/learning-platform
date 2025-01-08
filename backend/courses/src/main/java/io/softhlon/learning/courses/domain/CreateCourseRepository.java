// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
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
public interface CreateCourseRepository {
    CreateAccountRequest execute(CreateAccountRequest request);

    record CreateAccountRequest(
          UUID courseId,
          String name,
          String description,
          String content,
          String version) {}

    sealed interface CreateAccountResult {
        record AccountPersisted() implements CreateAccountResult {}
        record AccountPersistenceFailed(Throwable cause) implements CreateAccountResult {}
    }
}
