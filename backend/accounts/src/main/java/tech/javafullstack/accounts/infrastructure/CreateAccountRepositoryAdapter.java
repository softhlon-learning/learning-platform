// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CreateAccountRepository;
import tech.javafullstack.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import tech.javafullstack.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import tech.javafullstack.common.hexagonal.PersistenceAdapter;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create account repository adapter implementation.
 */
@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateAccountRepositoryAdapter implements CreateAccountRepository {

    private final AccountsJpaRepository accountsRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateAccountResult execute(
          String type,
          String name,
          String email,
          String password,
          boolean active) {

        try {
            var createdAccount = accountsRepo.save(
                  AccountEntity.builder()
                        .type(type)
                        .name(name)
                        .email(email)
                        .password(password)
                        .isActive(active)
                        .build()
            );

            return new AccountPersisted(
                  createdAccount.getId());
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new AccountPersistenceFailed(cause);
        }

    }

}
