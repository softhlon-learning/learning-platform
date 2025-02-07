// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Course JAP entity.
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "courses", schema = "_courses")
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
