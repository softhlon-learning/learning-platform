// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tech.softhlon.learning.accounts.AccountsConfiguration;
import tech.softhlon.learning.common.security.AuthenticationContext;
import tech.softhlon.learning.courses.CoursesConfiguration;
import tech.softhlon.learning.subscriptions.SubscriptionsConfiguration;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

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
