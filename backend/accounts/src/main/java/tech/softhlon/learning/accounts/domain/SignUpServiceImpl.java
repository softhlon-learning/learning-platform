// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailRequest;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountExists;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.CheckAccountFailed;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountRequest;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import tech.softhlon.learning.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import tech.softhlon.learning.accounts.domain.SignUpService.Result.*;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
@RequiredArgsConstructor
class SignUpServiceImpl implements SignUpService {

    private static final String ACCOUNT_ALREADY_EXISTS = "Account with the same email already exists";
    private static final String ACCOUNT_TS_DELETED = "Account has been deleted before";
    private static final String PASSWORD_POLICY =
          "Password should have 12 characters or more, at least " +
                "one lower case letter, one upper case letter, and digit";

    private final EmailValidationService emailValidationService;
    private final PasswordValidationService passwordValidationService;
    private final CreateAccountRepository createAccountRepository;
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;
    private final JwtService jwtService;

    @Override
    public Result execute(
          Request request) {

        var validationResult = validateInput(
              request);

        if (validationResult != null)
            return validationResult;

        var exists = checkAccountByEmailRepository.execute(
              new CheckAccountByEmailRequest(
                    request.email()));

        return switch (exists) {
            case AccountExists(_) -> new AccountAlreadyExistsFailed(ACCOUNT_ALREADY_EXISTS);
            case AccountIsDeleted() -> new AccountIsDeletedFailed(ACCOUNT_TS_DELETED);
            case AccountNotFound() -> persistAccount(request);
            case CheckAccountFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          Request request) {

        if (request.name().isBlank())
            return new NamePolicyFailed("Name is blank");

        if (request.email().isBlank())
            return new EmailPolicyFailed("Email is blank");

        if (!emailValidationService.isEmailValid(request.email()))
            return new EmailPolicyFailed("Email is not in right format");

        if (!passwordValidationService.isPasswordValid(request.password()))
            return new PasswordPolicyFailed(PASSWORD_POLICY);

        return null;

    }

    private Result persistAccount(
          Request request) {

        var result = createAccountRepository.execute(
              prepareRequest(request));

        return switch (result) {
            case AccountPersisted(UUID id) -> new Succeeded(id, token(request, id));
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private CreateAccountRequest prepareRequest(
          Request request) {

        return new CreateAccountRequest(
              AccountType.PASSWORD.name(),
              request.name(),
              request.email(),
              encryptPassword(request.password()));

    }

    private String encryptPassword(
          String password) {

        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);

    }

    private String token(Request request, UUID id) {

        return jwtService.generateToken(
              id,
              request.email());

    }

}
