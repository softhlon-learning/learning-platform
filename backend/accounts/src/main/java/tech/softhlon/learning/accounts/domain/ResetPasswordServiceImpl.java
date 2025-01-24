// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenRequest;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersisted;
import tech.softhlon.learning.accounts.domain.CreatePasswordTokenRepository.CreatePasswordTokenResult.PasswordTokenPersistenceFailed;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.Account;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailRequest;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountIsDeleted;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.AccountNotFound;
import tech.softhlon.learning.accounts.domain.LoadAccountByEmailRepository.LoadAccountByEmailResult.LoadAccountFailed;
import tech.softhlon.learning.accounts.domain.ResetPasswordService.Result.EmailNotFoundFailed;
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
    private final LoadAccountByEmailRepository loadAccountByEmailRepository;
    private final CreatePasswordTokenRepository createPasswordTokenRepository;
    private final EmailService emailService;
    private final String baseUrl;
    private static final String EMAIL_CONTENT = """
          Hello,
          
          We've received a request to reset your password. Please click the link below to set a new one:
          %s
          
          If you didn't request this, please ignore this email for your account's security.
          
          Best regards,
          Softhlon-Learning Team
          """;

    public ResetPasswordServiceImpl(
          LoadAccountByEmailRepository loadAccountByEmailRepository,
          CreatePasswordTokenRepository createPasswordTokenRepository,
          EmailService emailService,
          @Value("${password-recovery.base-url}") String baseUrl) {
        this.loadAccountByEmailRepository = loadAccountByEmailRepository;
        this.createPasswordTokenRepository = createPasswordTokenRepository;
        this.emailService = emailService;
        this.baseUrl = baseUrl;
    }

    @Override
    public Result execute(Request request) {
        var result = loadAccountByEmailRepository.execute(new LoadAccountByEmailRequest(request.email()));
        return switch (result) {
            case AccountFound(Account account) -> processPasswordRecovery(account);
            case AccountIsDeleted(), AccountNotFound() -> new EmailNotFoundFailed(EMAIL_NOT_FOUND);
            case LoadAccountFailed(Throwable cause) -> new Failed(cause);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result processPasswordRecovery(Account account) {
        var token = UUID.randomUUID().toString();
        var result = createPasswordTokenRepository.execute(passwordTokenRequest(account, token));
        return switch (result) {
            case PasswordTokenPersisted passwordTokenPersisted -> sendEmail(account, token);
            case PasswordTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };
    }

    private CreatePasswordTokenRequest passwordTokenRequest(Account account, String token) {
        return new CreatePasswordTokenRequest(account.id(), token, OffsetDateTime.now().plusDays(1));
    }

    private Result sendEmail(Account account, String token) {
        emailService.sendMessage(account.email(), SUBJECT, EMAIL_CONTENT.formatted(baseUrl + token));
        return new Succeeded();
    }
}
