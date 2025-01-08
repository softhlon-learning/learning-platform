// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import io.softhlon.learning.courses.domain.PersistCourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersisted;
import static io.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class PersistCourseRepositoryAdapter implements PersistCourseRepository {
    private final CoursesJpaRepository coursesRepo;

    @Override
    public PersistCourseResult execute(PersistCourseRequest request) {
        try {
            var entityOpt = coursesRepo.findById(request.id());
            if (entityOpt.isPresent()) {
                var entity = entityOpt.get();
                updateEntity(request, entity);
                coursesRepo.save(entity);
            } else {
                coursesRepo.save(prepareEntity(request));
            }

            return new CoursePersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CoursePersistenceFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(PersistCourseRequest request, CourseEntity entity) {
        entity.setName(request.name());
        entity.setOrderNo(request.orderNo());
        entity.setDescription(request.description());
        entity.setContent(request.content());
        entity.setVersion(request.version());
    }

    private CourseEntity prepareEntity(PersistCourseRequest request) {
        return CourseEntity.builder()
              .id(request.id())
              .orderNo(request.orderNo())
              .name(request.name())
              .description(request.description())
              .content(request.content())
              .version(request.version())
              .build();
    }
}
