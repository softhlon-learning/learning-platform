// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface UpdateEnrollmentService {
    Result execute(Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotEligibleFailed(String message) implements Result {}
        record EnrollmentNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
    record Request(
          UUID accountId,
          UUID courseId,
          String content) {}
}
