// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.*;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.gateway.operator.LoadAccountByEmailOperator.LoadAccountResult.AccountNotLoaded;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Load account by email operator.
 */
@Service
@RequiredArgsConstructor
public class LoadAccountByEmailOperator {

    private final LoadAccountByEmailRepository loadAccountByEmailRepository;

    /**
     * Load account by email.
     * @param email User's email
     * @return LoadAccountResult
     */
    public LoadAccountResult execute(String email) {

        var result = loadAccountByEmailRepository.execute(email);
        return switch (result) {
            case AccountFound(Account account) -> new AccountLoaded(accountView(account));
            case AccountIsNotActivated(), AccountIsDeleted(), AccountNotFound() -> new AccountNotLoaded();
            case LoadAccountFailed(Throwable cause) -> new AccountLoadFailed(cause);
        };

    }

    public sealed interface LoadAccountResult {
        record AccountLoaded(AccountView account) implements LoadAccountResult {}
        record AccountNotLoaded() implements LoadAccountResult {}
        record AccountLoadFailed(Throwable cause) implements LoadAccountResult {}
    }

    public record AccountView(
          UUID id,
          String email) {}

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private AccountView accountView(
          Account account) {

        return new AccountView(
              account.id(),
              account.email());
    }

}
