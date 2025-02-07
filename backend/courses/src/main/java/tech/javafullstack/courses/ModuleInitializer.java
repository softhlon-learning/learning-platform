// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.courses;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import tech.javafullstack.courses.gateway.operator.CreateCoursesOperator;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Courses module initializer.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class ModuleInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CreateCoursesOperator createCoursesOperator;

    /**
     * Initialize courses module.
     * @param event ContextRefreshedEvent
     */
    @Override
    @SneakyThrows
    public void onApplicationEvent(
          ContextRefreshedEvent event) {

        log.info("Module Initializer started");
        createCoursesOperator.execute();
        log.info("Module Initializer finished");

    }

}
