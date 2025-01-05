// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import java.util.List;

public interface ListCoursesService {
    Result listCourses(Request request);
    record Request(String accountId, String courseId) {}

    sealed interface Result {
        record Success(List<Course> courses) implements Result {}
        record InternalFailure(Throwable cause) implements Result {}
    }

    record Course(
          String courseId,
          String name,
          String description) {}
}
