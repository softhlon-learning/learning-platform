// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class LoadPasswordTokenRepositoryAdapter implements LoadPasswordTokenRepository {

    @Override
    public LoadPasswordTokenResult execute(LoadPasswordTokenRequest request) {
        return null;
    }

}
