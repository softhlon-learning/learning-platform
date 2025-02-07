// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Email validation service interface.
 */
@FunctionalInterface
interface EmailValidationService {

    /**
     * Validate email format.
     * @param email Email to validate
     * @return True if valid
     */
    boolean isEmailValid(
          String email);

}
