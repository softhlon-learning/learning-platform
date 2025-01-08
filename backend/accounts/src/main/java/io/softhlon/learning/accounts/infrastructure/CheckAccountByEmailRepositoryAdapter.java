// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.infrastructure;

import io.softhlon.learning.accounts.domain.CheckAccountByEmailRepository;
import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.*;

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
            log.error("", cause);
            return new CheckAccountFailed(cause);
        }
    }
}
