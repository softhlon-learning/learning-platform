// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoaded;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenNotFound;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.PasswordToken;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.ExpiredTokenFailed;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.InvalidTokenFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountRequest;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.*;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdatePasswordServiceImpl implements UpdatePasswordService {
    private static final String INVALID_TOKEN = "Invalid reset password token";
    private static final String EXPIRED_TOKEN = "Reset password token has expired";

    private final LoadPasswordTokenRepository loadPasswordTokenRepository;
    private final LoadAccountRepository loadAccountRepository;
    private final DeletePasswordTokenRepository deletePasswordTokenRepository;

    @Override
    public Result execute(
          Request request) {

        var result = loadPasswordTokenRepository.execute(
              new LoadPasswordTokenRequest(request.token()));

        return switch (result) {
            case TokenLoaded(PasswordToken token) -> processTokenUpdate(token);
            case TokenNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processTokenUpdate(
          PasswordToken token) {

        if (token.expirationTime().isBefore(OffsetDateTime.now())) {
            return new ExpiredTokenFailed(EXPIRED_TOKEN);
        }

        var result = loadAccountRepository.execute(
              new LoadAccountRequest(token.accountId()));

        return switch (result) {
            case AccountLoaded(Account account) -> updatePassword(account);
            case AccountNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result updatePassword(Account account) {
        return null;
    }

}
