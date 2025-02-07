// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CreatePasswordTokenRepository;
import tech.javafullstack.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.javafullstack.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create password token repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class CreatePasswordTokenRepositoryAdapter implements CreatePasswordTokenRepository {

    private final PasswordTokensJpaRepository passwordTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreatePasswordTokenResult execute(
          UUID accountId,
          String token,
          OffsetDateTime expirationTime) {

        try {
            passwordTokensJpaRepository.save(
                  PasswordTokenEntity.builder()
                        .accountId(accountId)
                        .token(token)
                        .expireAt(expirationTime)
                        .build()
            );

            return new PasswordTokenPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new PasswordTokenPersistenceFailed(cause);
        }

    }

}
