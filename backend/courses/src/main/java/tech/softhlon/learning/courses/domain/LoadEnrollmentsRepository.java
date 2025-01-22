// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.domain;

import tech.softhlon.learning.common.domain.DomainRepository;
import tech.softhlon.learning.common.hexagonal.OutboundPort;

import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@OutboundPort
@DomainRepository
@FunctionalInterface
public interface LoadEnrollmentsRepository {
    ListEnrollmentsResult execute(ListEnrollmentsRequest request);

    record ListEnrollmentsRequest() {}
    sealed interface ListEnrollmentsResult {
        record EnrollmentsLoaded(List<Enrollment> enrollments) implements ListEnrollmentsResult {}
        record EnrollmentLoadFailed(Throwable cause) implements ListEnrollmentsResult {}
    }
    record Enrollment() {}
}
