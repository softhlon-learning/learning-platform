// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import tech.softhlon.learning.accounts.domain.InvalidateAuthTokenRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class InvalidateAuthTokenRepositoryAdapter implements InvalidateAuthTokenRepository {
    @Override
    public InvalidateAuthTokenResult execute(InvalidateAuthTokenRequest request) {
        throw new UnsupportedOperationException();
    }
}
