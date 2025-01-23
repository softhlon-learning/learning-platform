// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.infrastructure;

import org.springframework.stereotype.Service;
import tech.softhlon.learning.courses.domain.CreatePasswordTokenRepository;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class CreatePasswordTokenRepositoryAdapter implements CreatePasswordTokenRepository {
    @Override
    public CreatePasswordTokenResult execute(CreatePasswordTokenRequest request) {
        return null;
    }
}
