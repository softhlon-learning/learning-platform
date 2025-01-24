// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class LoadPasswordTokenRepositoryAdapter implements LoadPasswordTokenRepository {

    private final PasswordTokensJpaRepository passwordTokensJpaRepository;

    @Override
    public LoadPasswordTokenResult execute(LoadPasswordTokenRequest request) {

        try {

            var enity = passwordTokensJpaRepository.findByToken(
                  request.token());

            if (enity.isPresent()) {
                return new TokenLoaded(
                      toPasswordToken(enity.get()));
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
              entity.getToken(),
              entity.getExpirationTime());

    }

}
