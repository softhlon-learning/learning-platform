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
public interface EnrollCourseService {

    Result execute(
          UUID accountId,
          UUID courseId);

    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotSubscribedFailed(String message) implements Result {}
        record CourseNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
