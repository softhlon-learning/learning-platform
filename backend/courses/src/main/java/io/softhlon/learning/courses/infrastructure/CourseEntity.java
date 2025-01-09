// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Getter
@Setter
@Builder
@Entity(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
class CourseEntity {
    @Id
    private UUID id;
    private String code;
    private int orderNo;
    private String name;
    private String description;
    private String content;
    private String version;
}
