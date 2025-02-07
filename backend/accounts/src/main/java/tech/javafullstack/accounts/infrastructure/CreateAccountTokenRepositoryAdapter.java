// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CreateAccountTokenRepository;
import tech.javafullstack.accounts.domain.CreateAccountTokenRepository.CreateAccountTokenResult.AccountTokenPersisted;
import tech.javafullstack.accounts.domain.CreateAccountTokenRepository.CreateAccountTokenResult.AccountTokenPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create account activation token repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class CreateAccountTokenRepositoryAdapter implements CreateAccountTokenRepository {

    private final AccountActivationTokensJpaRepository accountTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateAccountTokenResult execute(
          UUID accountId,
          String token,
          OffsetDateTime expirationTime) {

        try {
            accountTokensJpaRepository.save(
                  AccountTokenEntity.builder()
                        .accountId(accountId)
                        .token(token)
                        .expireAt(expirationTime)
                        .build()
            );

            return new AccountTokenPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new AccountTokenPersistenceFailed(cause);
        }

    }

}
