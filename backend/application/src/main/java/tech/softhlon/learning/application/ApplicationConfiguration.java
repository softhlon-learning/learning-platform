// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tech.javafullstack.accounts.AccountsConfiguration;
import tech.javafullstack.common.security.AuthenticationContext;
import tech.javafullstack.courses.CoursesConfiguration;
import tech.javafullstack.subscriptions.SubscriptionsConfiguration;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Application configuration configuration.
 */
@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
@Import({
      AccountsConfiguration.class,
      CoursesConfiguration.class,
      SubscriptionsConfiguration.class
})
class ApplicationConfiguration {

    @Bean
    AuthenticationContext authenticationContext() {
        return new AuthenticationContext();
    }

}
