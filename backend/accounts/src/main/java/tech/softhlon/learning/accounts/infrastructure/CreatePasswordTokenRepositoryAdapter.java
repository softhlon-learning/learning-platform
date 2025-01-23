// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class CreatePasswordTokenRepositoryAdapter implements CreatePasswordTokenRepository {
    private final PasswordTokensJpaRepository passwordTokensJpaRepository;

    @Override
    public CreatePasswordTokenResult execute(CreatePasswordTokenRequest request) {
        try {
            passwordTokensJpaRepository.save(passwordTokenEntity(request));
            return new PasswordTokenPersisted();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new PasswordTokenPersistenceFailed(cause);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private PasswordTokenEntity passwordTokenEntity(CreatePasswordTokenRequest request) {
        return PasswordTokenEntity.builder()
              .accountId(request.accountId())
              .token(request.token())
              .expirationTime(request.expirationTime())
              .build();
    }
}
