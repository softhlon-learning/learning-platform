// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.*;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account by email repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class LoadAccountByEmailRepositoryAdapter implements LoadAccountByEmailRepository {

    private final AccountsJpaRepository accountsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public LoadAccountByEmailResult execute(
          String email) {

        try {
            var entity = accountsRepo.findByEmail(email);

            return entity.isPresent()
                  ? existingAccount(entity.get())
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new LoadAccountFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private LoadAccountByEmailResult existingAccount(
          AccountEntity entity) {

        if (entity.isDeleted()) return new AccountIsDeleted();
        if (entity.isActive()) return new AccountIsNotActivated();

        return new AccountFound(toAccount(entity));

    }

    private Account toAccount(
          AccountEntity entity) {

        return new Account(
              entity.getId(),
              entity.getEmail(),
              entity.getPassword()
        );

    }

}
