// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UpdateEnrolledCourseService {
    Result updateCourse(Request request);
    record Request(String accountId, String courseId, String content) {}

    sealed interface Result {
        record Success() implements Result {}
        record AccountNotEligible() implements Result {}
        record CourseNotFound() implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }
}
