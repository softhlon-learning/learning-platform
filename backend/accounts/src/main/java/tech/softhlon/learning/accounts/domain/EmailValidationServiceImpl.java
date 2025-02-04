// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Email validation service implementation.
 */
@Service
class EmailValidationServiceImpl implements EmailValidationService {

    private final Pattern emailPattern =
          Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmailValid(
          String email) {

        return emailPattern
              .matcher(email)
              .matches();

    }

}
