// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.accounts.gateway.LoadAccountByEmailOperator.Result.AccountLoadFailed;
import tech.softhlon.learning.accounts.gateway.LoadAccountByEmailOperator.Result.AccountLoaded;
import tech.softhlon.learning.accounts.gateway.LoadAccountByEmailOperator.Result.AccountNotLoaded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
public class LoadAccountByEmailOperator {

    private final LoadAccountByEmailRepository loadAccountByEmailRepository;

    Result execute(String email) {
        var result = loadAccountByEmailRepository.execute(email);

        return switch (result) {
            case AccountFound(Account account) -> new AccountLoaded(accountView(account));
            case AccountIsDeleted(), AccountNotFound() -> new AccountNotLoaded();
            case LoadAccountFailed(Throwable cause) -> new AccountLoadFailed(cause);
        };
    }

    sealed interface Result {
        record AccountLoaded(AccountView account) implements Result {}
        record AccountNotLoaded() implements Result {}
        record AccountLoadFailed(Throwable cause) implements Result {}
    }

    record AccountView(
          UUID id,
          String email) {}

    private AccountView accountView(
          Account account) {

        return new AccountView(
              account.id(),
              account.email());
    }
}
