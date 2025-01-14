// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import tech.softhlon.learning.courses.domain.CreateEnrollmentRepository;
import tech.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollementPersistenceFailed;
import tech.softhlon.learning.courses.domain.CreateEnrollmentRepository.CreateEnrollmentResult.EnrollmentPersisted;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateEnrollmentRepositoryAdapter implements CreateEnrollmentRepository {
    private final EnrollmentsJpaRepository enrollmentsRepo;
    private final CoursesJpaRepository coursesRepo;

    @Override
    public CreateEnrollmentResult execute(CreateEnrollmentRequest request) {
        try {
            var course = coursesRepo.findById(request.courseId()).get();
            var createdEnrollment = enrollmentsRepo.save(toEnrollment(request, course));
            return new EnrollmentPersisted(createdEnrollment.getId());
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new EnrollementPersistenceFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private EnrollmentEntity toEnrollment(
          CreateEnrollmentRequest request,
          CourseEntity course) {
        return EnrollmentEntity.builder()
              .accountId(request.accountId())
              .course(course)
              .status(request.status())
              .content(course.getContent())
              .enrolledTime(request.enrolledTime())
              .build();
    }
}
