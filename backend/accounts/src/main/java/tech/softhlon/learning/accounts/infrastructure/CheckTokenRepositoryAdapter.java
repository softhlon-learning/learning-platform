// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CheckTokenRepository;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;

import static tech.javafullstack.accounts.domain.CheckTokenRepository.CheckTokenResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Check password token repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckTokenRepositoryAdapter implements CheckTokenRepository {

    private final InvalidatedTokensJpaRepository invalidatedTokensRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckTokenResult execute(
          String tokenHash) {

        try {
            return invalidatedTokensRepo.existsByTokenHash(
                  tokenHash)
                  ? new TokenExists()
                  : new TokenNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckTokenFailed(cause);
        }

    }

}
