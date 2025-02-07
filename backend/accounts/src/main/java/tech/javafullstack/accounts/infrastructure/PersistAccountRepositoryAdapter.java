// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.javafullstack.accounts.domain.PersistAccountRepository;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Persist account repository adapter implementation.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class PersistAccountRepositoryAdapter implements PersistAccountRepository {

    private final AccountsJpaRepository accountsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersistAccountResult execute(
          PersistAccountRequest request) {

        try {
            var entity = accountsJpaRepository.findById(request.id());

            if (entity.isEmpty())
                return new AccountNotFoundInDatabase();

            updateEntity(
                  entity.get(),
                  request
            );

            accountsJpaRepository.save(entity.get());
            return new AccountPersisted(request.id());

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new AccountPersistenceFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private void updateEntity(
          AccountEntity entity,
          PersistAccountRequest request) {

        entity.setName(request.name());
        entity.setType(request.type());
        entity.setEmail(request.email());
        entity.setPassword(request.password());
        entity.setDeleted(request.isDeleted());

    }

}
