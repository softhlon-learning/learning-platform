// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdateFailed;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdated;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update account active flag repository adapter implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
class UpdateAccountActiveFlagRepositoryAdapter implements UpdateAccountActiveFlagRepository {

    private final AccountTokensJpaRepository accountTokensJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateActiveFlagResult execute(
          UUID accountId,
          boolean isActive) {

        try {

            accountTokensJpaRepository.updateIsActive(accountId, isActive);
            return new ActiveFlagUpdated();

        } catch (Throwable cause) {
            log.info("Error", cause);
            return new ActiveFlagUpdateFailed(cause);
        }

    }

}
