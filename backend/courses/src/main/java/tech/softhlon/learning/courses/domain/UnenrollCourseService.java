// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Unenroll course service interface.
 */
public interface UnenrollCourseService {

    /**
     * Unenroll course.
     * @param accountId Account Id
     * @param courseId  Course Id
     * @return Result
     */
    Result execute(
          UUID accountId,
          UUID courseId);

    /**
     * Unenroll course result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record EnrollmentNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
