// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.application;

import io.softhlon.learning.accounts.AccountsConfiguration;
import io.softhlon.learning.common.security.AuthenticationContext;
import io.softhlon.learning.courses.CoursesConfiguration;
import io.softhlon.learning.subscriptions.SubscriptionsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
@Import({
      AccountsConfiguration.class,
      CoursesConfiguration.class,
      SubscriptionsConfiguration.class,
      WebSecurityConfig.class
})
class ApplicationConfiguration {
    @Bean
    AuthenticationContext authenticationContext() {
        return new AuthenticationContext();
    }
}
