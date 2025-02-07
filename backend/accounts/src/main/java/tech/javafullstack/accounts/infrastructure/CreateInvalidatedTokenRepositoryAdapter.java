// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CreateInvalidatedTokenRepository;

import static tech.javafullstack.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersisted;
import static tech.javafullstack.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create invalidated token repository adapter implementation.
 */
@Slf4j
@Service
@tech.javafullstack.common.hexagonal.PersistenceAdapter
@RequiredArgsConstructor
class CreateInvalidatedTokenRepositoryAdapter implements CreateInvalidatedTokenRepository {

    private final InvalidatedTokensJpaRepository invalidatedTokensRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateInvalidatedTokenResult execute(
          String tokenHash) {

        try {
            var createdAccount = invalidatedTokensRepo.save(
                  InvalidatedTokenEntity.builder()
                        .tokenHash(tokenHash)
                        .build()
            );

            return new InvalidatedTokenPersisted(
                  createdAccount.getId());
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new InvalidatedTokenPersistenceFailed(cause);
        }

    }

}
