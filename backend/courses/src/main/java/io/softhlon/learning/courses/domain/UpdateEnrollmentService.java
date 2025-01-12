// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface UpdateEnrollmentService {
    Result update(Request request);

    record Request(
          UUID accountId,
          UUID courseId,
          String content) {}

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotEligibleFailed(String message) implements Result {}
        record EnrollmentNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}
