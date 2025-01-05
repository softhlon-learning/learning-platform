// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Configuration(proxyBeanMethods = false)
@ComponentScan("io.softhlon.learning.courses")
public class CoursesConfiguration {
}
