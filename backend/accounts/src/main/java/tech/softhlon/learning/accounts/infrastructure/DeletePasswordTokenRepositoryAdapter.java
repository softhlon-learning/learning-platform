// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeleted;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeletionFailed;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@RequiredArgsConstructor
class DeletePasswordTokenRepositoryAdapter implements DeletePasswordTokenRepository {

    private final PasswordTokensJpaRepository passwordTokensJpaRepository;

    @Override
    public DeletePasswordTokenResult execute(
          UUID id) {

        try {

            passwordTokensJpaRepository.deleteById(id);
            return new TokenDeleted();

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new TokenDeletionFailed(cause);

        }

    }

}
