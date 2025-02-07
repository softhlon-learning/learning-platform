// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository.DeleteAccountTokenResult.TokenDeleted;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository.DeleteAccountTokenResult.TokenDeletionFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Delete account activation token repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class DeleteAccountTokenRepositoryAdapter implements DeleteAccountTokenRepository {

    private final AccountTokensJpaRepository accountTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public DeleteAccountTokenResult execute(
          UUID accountId) {

        try {
            accountTokensJpaRepository.deleteByAccountId(accountId);
            return new TokenDeleted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new TokenDeletionFailed(cause);
        }

    }

}
