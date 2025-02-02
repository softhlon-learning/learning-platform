// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

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
        record LectureNotFoundFailed() implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          UUID accountId,
          UUID courseId,
          String lectureId,
          boolean processed) {}

}
