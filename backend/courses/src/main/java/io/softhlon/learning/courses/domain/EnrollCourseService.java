// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import io.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface EnrollCourseService {
    Result enroll(Request request);

    record Request(String accountId, String courseId) {}
    sealed interface Result {
        record Succeeded() implements Result {}
        record AccountNotSubscribedFailed(String message) implements Result {}
        record CourseNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }
}
