// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Repository
interface EnrollmentsJpaRepository extends CrudRepository<EnrollmentEntity, UUID> {
    boolean existsByAccountIdAndCourseId(UUID accountId, UUID courseId);
    Optional<EnrollmentEntity> findByAccountIdAndCourseId(UUID accountId, UUID courseId);
    void deleteByAccountIdAndCourseId(UUID accountId, UUID courseId);
    List<EnrollmentEntity> findByCourseId(UUID accountId, UUID courseId);
}
