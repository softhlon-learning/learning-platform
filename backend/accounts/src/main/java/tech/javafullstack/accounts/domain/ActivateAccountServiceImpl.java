// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.ExpiredTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Failed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.InvalidTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Succeeded;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository.DeleteAccountTokenResult.TokenDeleted;
import tech.javafullstack.accounts.domain.DeleteAccountTokenRepository.DeleteAccountTokenResult.TokenDeletionFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.Account;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.AccountToken;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenNotFound;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdateFailed;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdated;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Activate account service implementation.
 */
@Service
@Transactional
@RequiredArgsConstructor
class ActivateAccountServiceImpl implements ActivateAccountService {

    private static final String INVALID_TOKEN = "Invalid account activation token";
    private static final String EXPIRED_TOKEN = "Account activation token has expired";

    private final LoadAccountTokenRepository loadAccountTokenRepository;
    private final UpdateAccountActiveFlagRepository updateAccountActiveFlagRepository;
    private final LoadAccountRepository loadAccountRepository;
    private final DeleteAccountTokenRepository deleteAccountTokenRepository;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String token) {

        var result = loadAccountTokenRepository.execute(token);
        return switch (result) {
            case TokenLoaded(AccountToken accountToken) -> activateAccount(accountToken);
            case TokenNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result activateAccount(
          AccountToken accountToken) {

        if (accountToken.expirationTime().isBefore(OffsetDateTime.now())) {
            return new ExpiredTokenFailed(EXPIRED_TOKEN);
        }

        var result = updateAccountActiveFlagRepository.execute(
              accountToken.accountId(),
              true
        );

        return switch (result) {
            case ActiveFlagUpdated() -> loadAcount(accountToken.accountId());
            case ActiveFlagUpdateFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result loadAcount(UUID accountId) {
        var result = loadAccountRepository.execute(accountId);

        return switch (result) {
            case AccountLoaded(Account account) -> deleteToken(account);
            case AccountNotFound() -> new Failed(null);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result deleteToken(
          Account account) {

        var result = deleteAccountTokenRepository.execute(account.id());
        return switch (result) {
            case TokenDeleted tokenDeleted -> authToken(account);
            case TokenDeletionFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result authToken(
          Account account) {

        var authToken = jwtService.generateToken(
              account.id(),
              account.email()
        );
        return new Succeeded(authToken);

    }

}
