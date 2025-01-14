// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.infrastructure;

import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository;
import tech.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Slf4j
@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CheckAccountByEmailRepositoryAdapter implements CheckAccountByEmailRepository {
    private final AccountsJpaRepository accountsRepo;

    @Override
    public CheckAccountByEmailResult execute(CheckAccountByEmailRequest request) {
        try {
            return accountsRepo.existsByEmail(request.email())
                  ? new AccountExists()
                  : new AccountNotFound();
        } catch (Throwable cause) {
            log.error("Error", cause);
            return new CheckAccountFailed(cause);
        }
    }
}
