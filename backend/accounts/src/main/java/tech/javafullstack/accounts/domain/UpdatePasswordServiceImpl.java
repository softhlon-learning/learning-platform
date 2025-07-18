// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeleted;
import tech.javafullstack.accounts.domain.DeletePasswordTokenRepository.DeletePasswordTokenResult.TokenDeletionFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.Account;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoadFailed;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountLoaded;
import tech.javafullstack.accounts.domain.LoadAccountRepository.LoadAccountResult.AccountNotFound;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoadFailed;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenLoaded;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.LoadPasswordTokenResult.TokenNotFound;
import tech.javafullstack.accounts.domain.LoadPasswordTokenRepository.PasswordToken;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountRequest;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountNotFoundInDatabase;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersisted;
import tech.javafullstack.accounts.domain.PersistAccountRepository.PersistAccountResult.AccountPersistenceFailed;
import tech.javafullstack.accounts.domain.UpdatePasswordService.Result.*;

import java.time.OffsetDateTime;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update password service implementation.
 */
@Service
@RequiredArgsConstructor
class UpdatePasswordServiceImpl implements UpdatePasswordService {

    private static final String INVALID_TOKEN = "Invalid password token";
    private static final String EXPIRED_TOKEN = "Password token has expired";
    private static final String PASSWORD_POLICY =
          "Password should have 12 characters or more, at least " +
                "one lower case letter, one upper case letter, and digit";

    private final LoadPasswordTokenRepository loadPasswordTokenRepository;
    private final LoadAccountRepository loadAccountRepository;
    private final PersistAccountRepository persistAccountRepository;
    private final DeletePasswordTokenRepository deletePasswordTokenRepository;
    private final PasswordValidationService passwordValidationService;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String token,
          String password) {

        var validationResult = validateInput(
              token,
              password
        );

        if (validationResult != null)
            return validationResult;

        var result = loadPasswordTokenRepository
              .execute(token);

        return switch (result) {
            case TokenLoaded(PasswordToken passwordToken) -> processTokenUpdate(token, password, passwordToken);
            case TokenNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case TokenLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          String token,
          String password) {

        if (!passwordValidationService.isPasswordValid(password))
            return new PasswordPolicyFailed(PASSWORD_POLICY);

        return null;

    }

    private Result processTokenUpdate(
          String token,
          String password,
          PasswordToken passwordToken) {

        if (passwordToken.expirationTime().isBefore(OffsetDateTime.now())) {
            return new ExpiredTokenFailed(EXPIRED_TOKEN);
        }

        var result = loadAccountRepository.execute(passwordToken.accountId());
        return switch (result) {
            case AccountLoaded(Account account) -> updatePassword(password, passwordToken, account);
            case AccountNotFound() -> new InvalidTokenFailed(INVALID_TOKEN);
            case AccountLoadFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result updatePassword(
          String password,
          PasswordToken token,
          Account account) {

        var result = persistAccountRepository.execute(
              new PersistAccountRequest(
                    account.id(),
                    account.type(),
                    account.name(),
                    account.email(),
                    encryptPassword(password),
                    account.isDeleted()
              )
        );

        return switch (result) {
            case AccountPersisted(_) -> deleteToken(account);
            case AccountNotFoundInDatabase() -> new InvalidTokenFailed(INVALID_TOKEN);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result deleteToken(Account account) {

        var result = deletePasswordTokenRepository.execute(account.id());
        return switch (result) {
            case TokenDeleted tokenDeleted -> new Succeeded(authToken(account));
            case TokenDeletionFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private String encryptPassword(
          String password) {

        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);

    }

    private String authToken(Account account) {

        return jwtService.generateToken(
              account.id(),
              account.email()
        );

    }
}
