// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class MergeCourseServiceImpl implements MergeCourseService {
    @Override
    public MergeCourseResult execute(MergeCourseReuqest reuqest) {
        return new CourseMerged();
    }
}
