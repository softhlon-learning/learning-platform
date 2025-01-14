// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.infrastructure;

import io.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@tech.softhlon.learning.common.hexagonal.PersistenceAdapter
@RequiredArgsConstructor
class CreateInvalidatedTokenRepositoryAdapter implements CreateInvalidatedTokenRepository {
    @Override
    public CreateInvalidatedTokenResult execute(CreateInvalidatedTokenRequest request) {
        return null;
    }
}
