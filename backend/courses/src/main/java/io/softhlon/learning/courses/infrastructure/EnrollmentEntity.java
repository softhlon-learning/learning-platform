// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Getter
@Setter
@Builder
@Entity(name = "enrollments")
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEntity {
    @Id
    private UUID id;
    @ManyToOne
    private CourseEntity course;
    private String status;
    private String content;
    private OffsetDateTime enrolledTime;
    private OffsetDateTime unenrolledTime;
    private OffsetDateTime completedTime;
}
