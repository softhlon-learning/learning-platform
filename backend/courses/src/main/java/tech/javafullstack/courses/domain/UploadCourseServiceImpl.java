// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.courses.domain.MergeCourseService.MergeCourseResult.CourseMergeFailed;
import tech.javafullstack.courses.domain.MergeCourseService.MergeCourseResult.CourseMerged;
import tech.javafullstack.courses.domain.MergeCourseService.MergeCourseReuqest;
import tech.javafullstack.courses.domain.PersistCourseRepository.PersistCourseRequest;
import tech.javafullstack.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersisted;
import tech.javafullstack.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersistenceFailed;
import tech.javafullstack.courses.domain.UploadCourseService.Result.Failed;
import tech.javafullstack.courses.domain.UploadCourseService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Upload course service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class UploadCourseServiceImpl implements UploadCourseService {

    private final MergeCourseService mergeCourseService;
    private final PersistCourseRepository persistCourseRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          Request request) {

        try {
            var result = persistCourseRepository.execute(prepareReuqest(request));
            return switch (result) {
                case CoursePersisted() -> mergeCourseWithEnrollments(request);
                case CoursePersistenceFailed(Throwable cause) -> new Failed(cause);
            };
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new Failed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private PersistCourseRequest prepareReuqest(
          Request request) {

        return new PersistCourseRequest(
              request.courseId(),
              request.code(),
              request.orderNo(),
              request.name(),
              request.description(),
              request.content(),
              request.version()
        );

    }

    private Result mergeCourseWithEnrollments(
          Request request) {

        var result = mergeCourseService.execute(prepareMergeRequest(request));
        return switch (result) {
            case CourseMergeFailed(Throwable cause) -> new Failed(cause);
            case CourseMerged() -> new Succeeded();
        };

    }

    private MergeCourseReuqest prepareMergeRequest(
          Request request) {

        return new MergeCourseReuqest(
              request.courseId(),
              request.code(),
              request.orderNo(),
              request.name(),
              request.description(),
              request.content(),
              request.version()
        );

    }
}
