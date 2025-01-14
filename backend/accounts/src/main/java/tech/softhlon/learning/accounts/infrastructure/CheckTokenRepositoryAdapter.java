// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckTokenRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;

import static tech.softhlon.learning.accounts.domain.CheckTokenRepository.CheckTokenResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckTokenRepositoryAdapter implements CheckTokenRepository {
    private final InvalidatedTokensJpaRepository invalidatedTokensRepo;

    @Override
    public CheckTokenResult execute(CheckTokenRequest request) {
        try {
            return invalidatedTokensRepo.existsByTokenHash(request.tokenHash())
                  ? new TokenExists()
                  : new TokenNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckTokenFailed(cause);
        }
    }
}
