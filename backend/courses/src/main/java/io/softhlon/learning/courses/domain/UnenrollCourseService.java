// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UnenrollCourseService {
    Result unenroll(Request request);
    record Request(String accountId, String courseId) {}

    sealed interface Result {
        record Success() implements Result {}
        record CourseNotFound() implements Result {}
        record AccountNotEnrolled() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
