// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.PersistCourseRepository;
import tech.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersisted;
import tech.softhlon.learning.courses.domain.PersistCourseRepository.PersistCourseResult.CoursePersistenceFailed;

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
    public PersistCourseResult execute(
          PersistCourseRequest request) {

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

    private void updateEntity(
          PersistCourseRequest request,
          CourseEntity entity) {

        entity.setCode(request.code());
        entity.setName(request.name());
        entity.setOrderNo(request.orderNo());
        entity.setDescription(request.description());
        entity.setContent(request.content());
        entity.setVersion(request.version());

    }

    private CourseEntity prepareEntity(
          PersistCourseRequest request) {

        return CourseEntity.builder()
              .id(request.id())
              .code(request.code())
              .name(request.name())
              .orderNo(request.orderNo())
              .description(request.description())
              .content(request.content())
              .version(request.version())
              .build();

    }

}
