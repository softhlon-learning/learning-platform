// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface UpdateLectureService {

    Result execute(
          Request request);

    sealed interface Result {
        record Succeeded() implements Result {}
        record LectureNotFoundFailed(String messagw) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          UUID courseId,
          boolean processed) {}
}
