// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;

import java.util.List;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class MergeCourseServiceImpl implements MergeCourseService {
    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        return new CourseMerged();
    }

    record CourseContent(List<Chapter> chapters) {}

    record Chapter(
          String name,
          List<Lecture> lectures) {}

    record Lecture(
          UUID id,
          String name,
          String type,
          boolean processed,
          String time,
          boolean selctected) {}
}

