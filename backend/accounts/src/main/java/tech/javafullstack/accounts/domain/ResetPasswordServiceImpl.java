// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.javafullstack.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.javafullstack.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.EmailNotFoundFailed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.EmailPolicyFailed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.Failed;
import tech.javafullstack.accounts.domain.ResetPasswordService.Result.Succeeded;

import java.time.OffsetDateTime;
import java.util.UUID;

import static tech.javafullstack.accounts.domain.EmailTemplates.RESET_PASSWORD_TEMPLATE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Reset password service implementation.
 */
@Service
class ResetPasswordServiceImpl implements ResetPasswordService {

    private static final String EMAIL_NOT_FOUND = "Email not found";
    private static final String SUBJECT = "Recover Your Account in Java FullStack Academy";

    private final LoadAccountByEmailRepository loadAccountByEmailRepository;
    private final CreatePasswordTokenRepository createPasswordTokenRepository;
    private final EmailValidationService emailValidationService;
    private final EmailService emailService;
    private final String baseUrl;

    public ResetPasswordServiceImpl(
          LoadAccountByEmailRepository loadAccountByEmailRepository,
          CreatePasswordTokenRepository createPasswordTokenRepository,
          EmailValidationService emailValidationService,
          EmailService emailService,
          @Value("${update-password.base-url}") String baseUrl) {

        this.loadAccountByEmailRepository = loadAccountByEmailRepository;
        this.createPasswordTokenRepository = createPasswordTokenRepository;
        this.emailValidationService = emailValidationService;
        this.emailService = emailService;
        this.baseUrl = baseUrl;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String email) {

        var validationResult = validateInput(email);

        if (validationResult != null)
            return validationResult;

        var result = loadAccountByEmailRepository
              .execute(email);

        return switch (result) {
            case AccountFound(Account account) -> resetPassword(account);
            case AccountIsDeleted(), AccountNotFound() -> new EmailNotFoundFailed(EMAIL_NOT_FOUND);
            case LoadAccountFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          String email) {

        if (email.isBlank())
            return new EmailPolicyFailed("Email is blank");

        if (!emailValidationService.isEmailValid(email))
            return new EmailPolicyFailed("Email is not in right format");

        return null;

    }

    private Result resetPassword(
          Account account) {

        var token = UUID.randomUUID().toString();
        var result = createPasswordTokenRepository.execute(
              account.id(),
              token,
              expirationTime()
        );

        return switch (result) {
            case PasswordTokenPersisted passwordTokenPersisted -> sendEmail(account, token);
            case PasswordTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private OffsetDateTime expirationTime() {

        return OffsetDateTime
              .now()
              .plusDays(1);

    }

    private Result sendEmail(
          Account account,
          String token) {

        emailService.sendMessage(
              account.email(),
              SUBJECT,
              RESET_PASSWORD_TEMPLATE.formatted(baseUrl + token)
        );

        return new Succeeded();

    }

}
