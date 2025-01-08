// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.courses;

import io.softhlon.learning.courses.gateway.CreateCoursesOperator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Component
@RequiredArgsConstructor
class ModuleInitializer implements ApplicationListener<ContextRefreshedEvent>  {
    private final CreateCoursesOperator createCoursesOperator;

    @Override
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Module Initializer started");
        createCoursesOperator.execute();
        log.info("Module Initializer finished");
    }
}
