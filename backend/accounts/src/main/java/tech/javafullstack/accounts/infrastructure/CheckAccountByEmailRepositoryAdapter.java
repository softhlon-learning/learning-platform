// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CheckAccountByEmailRepository;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;

import static tech.javafullstack.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Checkout account by email repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckAccountByEmailRepositoryAdapter implements CheckAccountByEmailRepository {

    private final AccountsJpaRepository accountsJpaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckAccountByEmailResult execute(
          String email) {

        try {

            var entity = accountsJpaRepository.findByEmail(email);
            return entity.isPresent()
                  ? existingAccount(entity.get())
                  : new AccountNotFound();

        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckAccountFailed(cause);
        }

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private CheckAccountByEmailResult existingAccount(
          AccountEntity entity) {

        return entity.isDeleted()
              ? new AccountIsDeleted()
              : new AccountExists(entity.getId());

    }

}
