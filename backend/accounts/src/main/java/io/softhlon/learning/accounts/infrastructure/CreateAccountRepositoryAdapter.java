// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.infrastructure;

import io.softhlon.learning.accounts.domain.CreateAccountRepository;
import io.softhlon.learning.accounts.domain.CreateAccountRepository.Result.InternalFailure;
import io.softhlon.learning.accounts.domain.CreateAccountRepository.Result.Success;
import io.softhlon.learning.common.hexagonal.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@PersistenceAdapter
@RequiredArgsConstructor
class CreateAccountRepositoryAdapter implements CreateAccountRepository {
    private final AccountsJpaRepository accountsJpaRepository;

    @Override
    public Result execute(Request request) {
        try {
            var createdAccount = accountsJpaRepository.save(toAccount(request));
            return new Success(createdAccount.getId());
        } catch (Throwable cause) {
            return new InternalFailure(cause);
        }
    }

    private AccountEntity toAccount(Request request) {
        return AccountEntity.builder()
              .name(request.name())
              .email(request.email())
              .password(request.password())
              .status(request.status())
              .build();
    }
}
