// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenNotFound;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load password token repositry adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadPasswordTokenRepositoryAdapter implements LoadPasswordTokenRepository {

    private final PasswordTokensJpaRepository passwordTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadPasswordTokenResult execute(String token) {

        try {
            var enity = passwordTokensJpaRepository
                  .findByToken(token);

            if (enity.isPresent()) {
                return new TokenLoaded(toPasswordToken(enity.get()));
            } else {
                return new TokenNotFound();
            }
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new TokenLoadFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private PasswordToken toPasswordToken(PasswordTokenEntity entity) {

        return new PasswordToken(
              entity.getId(),
              entity.getAccountId(),
              entity.getToken(),
              entity.getExpireAt());

    }

}
