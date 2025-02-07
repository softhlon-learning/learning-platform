// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Enrollment JPA entity.
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "enrollments", schema = "_courses")
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID accountId;
    @ManyToOne
    private CourseEntity course;
    private String content;
    private OffsetDateTime enrolledAt;
    private OffsetDateTime completedAt;

}
