// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID accountId;
    @ManyToOne
    private CourseEntity course;
    private String status;
    private String content;
    private OffsetDateTime enrolledTime;
    private OffsetDateTime completedTime;
}
