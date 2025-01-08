// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseRequest;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersisted;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersistenceFailed;
import static io.softhlon.learning.courses.domain.UploadCourseService.Result.Failed;
import static io.softhlon.learning.courses.domain.UploadCourseService.Result.Succeeded;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class UploadCourseServiceImpl implements UploadCourseService {
    private final PersistCourseRepository persistCourseRepository;

    @Override
    public Result execute(Request request) {
        try {
            var result = persistCourseRepository.execute(prepareReuqest(request));
            return switch (result) {
                case CoursePersisted() -> new Succeeded();
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

    private PersistCourseRequest prepareReuqest(Request request) {
        return new PersistCourseRequest(
              request.courseId(),
              request.orderNo(),
              request.name(),
              request.description(),
              request.content(),
              request.version()
        );
    }
}
