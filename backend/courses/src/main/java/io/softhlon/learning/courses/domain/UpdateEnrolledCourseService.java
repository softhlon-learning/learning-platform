// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

public interface UpdateEnrolledCourseService {
    Response updateCourse(Request request);
    record Request(String accountId, String courseId, String content) {}
    record Response(boolean success) {}
}
