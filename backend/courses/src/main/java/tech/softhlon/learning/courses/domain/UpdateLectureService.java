// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update lecture service interface.
 */
@InboundPort
@FunctionalInterface
public interface UpdateLectureService {

    /**
     * Update lecture.
     * @param request Operation request
     * @return Result
     */
    Result execute(
          Request request);

    /**
     * Update lecture result.
     */
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
