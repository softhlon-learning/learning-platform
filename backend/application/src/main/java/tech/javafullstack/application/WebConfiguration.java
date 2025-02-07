// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Web configuration.
 */
@Configuration
class WebConfiguration implements WebMvcConfigurer {

    /**
     * Add CORS mappings.
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(
          CorsRegistry registry) {

        registry
              .addMapping("/**")
              .allowedMethods("*");

    }

}
