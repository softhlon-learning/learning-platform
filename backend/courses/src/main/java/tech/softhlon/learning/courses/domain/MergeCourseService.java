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
 * Merge enrollment with the latest course service interface.
 */
interface MergeCourseService {

    /**
     * Merge enrollment with the latest course.
     * @param reuqest Operation request
     * @return MergeCourseResult
     */
    MergeCourseResult execute(
          MergeCourseReuqest reuqest);

    /**
     * Merge enrollment with the latest course result.
     */
    sealed interface MergeCourseResult {
        record CourseMerged() implements MergeCourseResult {}
        record CourseMergeFailed(Throwable cause) implements MergeCourseResult {}
    }

    record MergeCourseReuqest(
          UUID courseId,
          String code,
          int orderNo,
          String name,
          String description,
          String content,
          String version) {}

}
