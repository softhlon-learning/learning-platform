// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Password validation service interface.
 */
interface PasswordValidationService {

    /**
     * Check if password is in correct format.
     * @param password User's password
     * @return True if passsword format is correct
     */
    boolean isPasswordValid(
          String password);

}
