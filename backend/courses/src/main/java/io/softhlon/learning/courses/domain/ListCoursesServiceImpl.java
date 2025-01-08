// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Failed;
import static io.softhlon.learning.courses.domain.ListCoursesService.Result.Succeeded;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.Course;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoadFailed;
import static io.softhlon.learning.courses.domain.LoadCoursesRepository.LoadCoursesResult.CoursesLoaded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class ListCoursesServiceImpl implements ListCoursesService {
    private final LoadCoursesRepository loadCoursesRepository;

    @Override
    public Result listCourses() {
        var result = loadCoursesRepository.execute();
        return switch (result) {
            case CoursesLoadFailed(Throwable cause) -> new Failed(cause);
            case CoursesLoaded(List<Course> courses) -> new Succeeded(toCourseViews(courses));
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private List<CourseView> toCourseViews(List<Course> courses) {
        return courses.stream()
              .map(this::toCourseView)
              .sorted(Comparator.comparing(CourseView::orderNo))
              .toList();
    }

    private CourseView toCourseView(Course course) {
        return new CourseView(
              course.courseId(),
              course.orderNo(),
              course.name(),
              course.description(),
              course.content(),
              false);
    }
}
