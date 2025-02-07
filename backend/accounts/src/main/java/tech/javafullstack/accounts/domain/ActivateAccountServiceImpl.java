// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Failed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.InvalidTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.ExpiredTokenFailed;
import tech.javafullstack.accounts.domain.ActivateAccountService.Result.Succeeded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.AccountToken;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadAccountTokenRepository.LoadAccountTokenResult.TokenNotFound;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdateFailed;
import tech.javafullstack.accounts.domain.UpdateAccountActiveFlagRepository.UpdateActiveFlagResult.ActiveFlagUpdated;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Activate account service implementation.
 */
@Service
@RequiredArgsConstructor
class ActivateAccountServiceImpl implements ActivateAccountService {

    private static final String INVALID_TOKEN = "Invalid account activation token";
    private static final String EXPIRED_TOKEN = "Account activation token has expired";

    private final LoadAccountTokenRepository loadAccountTokenRepository;
    private final UpdateAccountActiveFlagRepository updateAccountActiveFlagRepository;

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
            case ActiveFlagUpdated() -> new Succeeded();
            case ActiveFlagUpdateFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
