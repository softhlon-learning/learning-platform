// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import io.softhlon.learning.common.hexagonal.InboundPort;

@InboundPort
@FunctionalInterface
public interface EnrollCourseService {
    Result enroll(Request request);

    record Request(String accountId, String courseId) {}
    sealed interface Result {
        record Success() implements Result {}
        record AccountNotSubscribed() implements Result {}
        record CourseNotFound() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
