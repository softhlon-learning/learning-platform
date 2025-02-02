// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.EmailNotFoundFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.EmailPolicyFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.Failed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.Succeeded;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Service
class ResetPasswordServiceImpl implements ResetPasswordService {

    private static final String EMAIL_NOT_FOUND = "Email not found";
    private static final String SUBJECT = "Reset Password Request";
    private static final String EMAIL_CONTENT = """
          Hello,
          
          We've received a request to reset your password. Please click the link below to set a new one:
          %s
          
          If you didn't request this, please ignore this email for your account's security.
          
          Best regards,
          Softhlon-Learning Team
          """;
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
              expirationTime());

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
              EMAIL_CONTENT.formatted(baseUrl + token));

        return new Succeeded();

    }

}
