// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenNotFound;


// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account token repository adapter implementation..
 */
@Slf4j
@Service
@RequiredArgsConstructor
class LoadAccountTokenRepositoryAdapter implements LoadAccountTokenRepository {

    private final AccountTokensJpaRepository accountTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadAccountTokenResult execute(
          String token) {

        try {
            var enity = accountTokensJpaRepository
                  .findByToken(token);

            if (enity.isPresent()) {
                return new TokenLoaded(accountToken(enity.get()));
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

    private AccountToken accountToken(AccountTokenEntity entity) {

        return new AccountToken(
              entity.getId(),
              entity.getAccountId(),
              entity.getToken(),
              entity.getExpireAt());

    }

}
