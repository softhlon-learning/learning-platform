// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class PasswordValidationServiceImpl implements PasswordValidationService {

    private final Pattern passwordCharsPattern =
          Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

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
