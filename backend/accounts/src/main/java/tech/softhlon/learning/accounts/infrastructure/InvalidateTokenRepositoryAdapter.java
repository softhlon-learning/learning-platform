// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.InvalidateTokenRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

import static tech.softhlon.learning.accounts.domain.InvalidateTokenRepository.InvalidateAuthTokenResult.TokenInvalidated;
import static tech.softhlon.learning.accounts.domain.InvalidateTokenRepository.InvalidateAuthTokenResult.TokenInvalidationFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class InvalidateTokenRepositoryAdapter implements InvalidateTokenRepository {
    private final InvalidatedTokensJpaRepository invalidatedJpaRepo;

    @Override
    public InvalidateAuthTokenResult execute(InvalidateAuthTokenRequest request) {
        try {
            var invalidatedToken = invalidatedJpaRepo.save(toToken(request));
            return new TokenInvalidated();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new TokenInvalidationFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private InvalidatedEntity toToken(InvalidateAuthTokenRequest request) {
        return InvalidatedEntity.builder()
              .tokenHash(request.authToken())
              .build();
    }
}
