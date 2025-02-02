// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface MergeCourseService {
    MergeCourseResult execute(
          MergeCourseReuqest reuqest);

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
