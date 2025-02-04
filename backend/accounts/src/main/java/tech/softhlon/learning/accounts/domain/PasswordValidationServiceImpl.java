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
 * Password validation service implementation.
 */
@Service
class PasswordValidationServiceImpl implements PasswordValidationService {

    private final Pattern passwordCharsPattern =
          Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPasswordValid(
          String password) {

        if (password.isBlank()) {
            return false;
        }
        if (password.length() < 12) {
            return false;
        }
        if (!passwordCharsPattern.matcher(password).matches()) {
            return false;
        }

        return true;

    }

}
