// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeleted;
import tech.softhlon.learning.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeletionFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountRequest;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.softhlon.learning.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoadFailed;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoaded;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenNotFound;
import tech.softhlon.learning.accounts.domain.LoadPasswordTokenRepository.PasswordToken;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountRequest;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;
import tech.softhlon.learning.accounts.domain.UpdatePasswordService.Result.*;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class UpdatePasswordServiceImpl implements UpdatePasswordService {

    private static final String INVALID_TOKEN = "Invalid password token";
    private static final String EXPIRED_TOKEN = "Password token has expired";
    private static final String PASSWORD_POLICY =
          "Password should have at 12 characters or more, at least " +
                "one lower case letter, one upper case letter, and digit";

    private final LoadPasswordTokenRepository loadPasswordTokenRepository;
    private final LoadAccountRepository loadAccountRepository;
    private final PersistAccountRepository persistAccountRepository;
    private final DeletePasswordTokenRepository deletePasswordTokenRepository;
    private final PasswordValidationService passwordValidationService;

    @Override
    public Result execute(
          Request request) {

        var validationResult = validateInput(
              request);

        var result = loadPasswordTokenRepository.execute(
              new LoadPasswordTokenRequest(request.token()));

        return switch (result) {
            case TokenLoaded(PasswordToken token) -> processTokenUpdate(request, token);
            case TokenNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          Request request) {

        if (!passwordValidationService.isPasswordValid(request.password()))
            return new PasswordPolicyFailed(PASSWORD_POLICY);

        return null;

    }


    private Result processTokenUpdate(
          Request request,
          PasswordToken token) {

        if (token.expirationTime().isBefore(OffsetDateTime.now())) {
            return new ExpiredTokenFailed(EXPIRED_TOKEN);
        }

        var result = loadAccountRepository.execute(
              new LoadAccountRequest(token.accountId()));

        return switch (result) {
            case AccountLoaded(Account account) -> updatePassword(request, token, account);
            case AccountNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result updatePassword(
          Request request,
          PasswordToken token,
          Account account) {

        var result = persistAccountRepository.execute(
              new PersistAccountRequest(
                    account.id(),
                    account.type(),
                    account.name(),
                    account.email(),
                    encryptPassword(request.password()),
                    account.isDeleted()
              ));

        return switch (result) {
            case AccountPersisted(_) -> deleteToken(token);
            case AccountNotFoundInDatabase() -> new InvalidTokenFailed(INVALID_TOKEN);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result deleteToken(PasswordToken token) {

        var result = deletePasswordTokenRepository.execute(
              new DeletePasswordTokenRequest(token.id()));

        return switch (result) {
            case TokenDeleted tokenDeleted -> new Succeeded();
            case TokenDeletionFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private String encryptPassword(
          String password) {

        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);

    }
}
