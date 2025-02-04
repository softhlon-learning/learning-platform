// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import static tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.*;
import static tech.softhlon.learning.accounts.domain.SignInService.Result.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign in with email and password service implementation.
 */
@Service
@RequiredArgsConstructor
class SignInServiceImpl implements SignInService {

    private static final String AUTH_ERORR_MESSAGE = "Authentication failed. Incorrect email or password";
    private final EmailValidationService emailValidationService;
    private final LoadAccountByEmailRepository loadAccountByEmailRepository;
    private final JwtService jwtService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String email,
          String password) {

        var validationResult = validateInput(
              email,
              password
        );

        if (validationResult != null)
            return validationResult;

        var exists = loadAccountByEmailRepository.execute(email);

        return switch (exists) {
            case AccountFound(Account account) -> authenticate(password, account);
            case AccountNotFound(), AccountIsDeleted() -> new InvalidCredentialsFailed(AUTH_ERORR_MESSAGE);
            case LoadAccountFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          String email,
          String password) {

        if (email.isBlank())
            return new EmailPolicyFailed("Email is blank");

        if (!emailValidationService.isEmailValid(email))
            return new EmailPolicyFailed("Email is not in right format");

        return null;

    }

    private Result authenticate(
          String password,
          Account account) {

        var passwordEncoder = new BCryptPasswordEncoder();
        var matches = passwordEncoder.matches(
              password,
              account.password()
        );

        return matches
              ? new Succeeded(token(account))
              : new InvalidCredentialsFailed(AUTH_ERORR_MESSAGE);

    }

    private String token(Account account) {

        return jwtService.generateToken(
              account.id(),
              account.email()
        );

    }

}
