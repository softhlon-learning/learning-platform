// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class PasswordValidationServiceImpl implements PasswordValidationService {
    @Override
    public boolean isPasswordValid(String password) {
        return false;
    }
}
