// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.infrastructure;

import io.softhlon.learning.accounts.domain.CheckAuthTokenRepository;
import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckAuthTokenRepositoryAdapter implements CheckAuthTokenRepository {
    @Override
    public CheckAuthTokenResult execute(CheckAuthTokenRequest request) {
        return null;
    }
}
