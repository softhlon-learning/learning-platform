// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository;

import static tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersisted;
import static tech.softhlon.learning.accounts.domain.CreateInvalidatedTokenRepository.CreateInvalidatedTokenResult.InvalidatedTokenPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@tech.softhlon.learning.common.hexagonal.PersistenceAdapter
@RequiredArgsConstructor
class CreateInvalidatedTokenRepositoryAdapter implements CreateInvalidatedTokenRepository {

    private final InvalidatedTokensJpaRepository invalidatedTokensRepo;

    @Override
    public CreateInvalidatedTokenResult execute(
          CreateInvalidatedTokenRequest request) {
        try {
            var createdAccount = invalidatedTokensRepo.save(
                  toInvalidatedToken(request));

            return new InvalidatedTokenPersisted(
                  createdAccount.getId());

        } catch (Throwable cause) {

            log.error("Error", cause);
            return new InvalidatedTokenPersistenceFailed(cause);

        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private InvalidatedEntity toInvalidatedToken(
          CreateInvalidatedTokenRequest request) {

        return InvalidatedEntity.builder()
              .tokenHash(request.tokenHash())
              .build();

    }

}
