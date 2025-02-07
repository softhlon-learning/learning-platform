// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountExists;
import tech.javafullstack.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountIsDeleted;
import tech.javafullstack.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.AccountNotFound;
import tech.javafullstack.accounts.domain.CheckAccountByEmailRepository.CheckAccountByEmailResult.CheckAccountFailed;
import tech.javafullstack.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersisted;
import tech.javafullstack.accounts.domain.CreateAccountRepository.CreateAccountResult.AccountPersistenceFailed;
import tech.javafullstack.accounts.domain.CreateAccountTokenRepository.CreateAccountTokenResult.AccountTokenPersisted;
import tech.javafullstack.accounts.domain.CreateAccountTokenRepository.CreateAccountTokenResult.AccountTokenPersistenceFailed;
import tech.javafullstack.accounts.domain.SignUpService.Result.*;
import tech.javafullstack.common.event.AccountCreated;

import java.time.OffsetDateTime;
import java.util.UUID;

import static tech.javafullstack.accounts.domain.AccountType.PASSWORD;
import static tech.javafullstack.accounts.domain.EmailTemplates.ACTIVATE_ACCOUNT_TEMPLATE;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign up service implementation.
 */
@Service
class SignUpServiceImpl implements SignUpService {
    private static final String NAME_IS_BLANK = "Name is blank";
    private static final String EMAIL_IS_BLANK = "Email is blank";
    private static final String EMAIL_INVALID_FORMAT = "Email is not in right format";
    private static final String ACCOUNT_ALREADY_EXISTS = "Account with the same email already exists";
    private static final String ACCOUNT_TS_DELETED = "Account has been deleted before";
    private static final String PASSWORD_POLICY =
          "Password should have 12 characters or more, at least " +
                "one lower case letter, one upper case letter, and digit";

    private static final String SUBJECT = "Activate Your Java FullStack Academy Account";

    private final EmailValidationService emailValidationService;
    private final PasswordValidationService passwordValidationService;
    private final CreateAccountRepository createAccountRepository;
    private final CheckAccountByEmailRepository checkAccountByEmailRepository;
    private final CreateAccountTokenRepository createAccountTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final String baseUrl;

    public SignUpServiceImpl(
          EmailValidationService emailValidationService,
          PasswordValidationService passwordValidationService,
          CreateAccountRepository createAccountRepository,
          CheckAccountByEmailRepository checkAccountByEmailRepository,
          CreateAccountTokenRepository createAccountTokenRepository,
          ApplicationEventPublisher applicationEventPublisher,
          EmailService emailService,
          JwtService jwtService,
          @Value("${activate-account.base-url}") String baseUrl) {

        this.emailValidationService = emailValidationService;
        this.passwordValidationService = passwordValidationService;
        this.createAccountRepository = createAccountRepository;
        this.checkAccountByEmailRepository = checkAccountByEmailRepository;
        this.createAccountTokenRepository = createAccountTokenRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.baseUrl = baseUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          String name,
          String email,
          String password) {

        var validationResult = validateInput(
              name,
              email,
              password
        );

        if (validationResult != null)
            return validationResult;

        var exists = checkAccountByEmailRepository.execute(email);

        return switch (exists) {
            case AccountExists(_) -> new AccountAlreadyExistsFailed(ACCOUNT_ALREADY_EXISTS);
            case AccountIsDeleted() -> new AccountIsDeletedFailed(ACCOUNT_TS_DELETED);
            case AccountNotFound() -> persistAccount(name, email, password);
            case CheckAccountFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          String name,
          String email,
          String password) {

        if (name.isBlank())
            return new NamePolicyFailed(NAME_IS_BLANK);

        if (email.isBlank())
            return new EmailPolicyFailed(EMAIL_IS_BLANK);

        if (!emailValidationService.isEmailValid(email))
            return new EmailPolicyFailed(EMAIL_INVALID_FORMAT);

        if (!passwordValidationService.isPasswordValid(password))
            return new PasswordPolicyFailed(PASSWORD_POLICY);

        return null;

    }

    private Result persistAccount(
          String name,
          String email,
          String password) {

        var result = createAccountRepository.execute(PASSWORD,
              name,
              email,
              encryptPassword(password)
        );

        return switch (result) {
            case AccountPersisted(UUID id) -> createAccountToken(id, email);
            case AccountPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Result createAccountToken(
          UUID id,
          String email) {
        var token = UUID.randomUUID().toString();
        var result = createAccountTokenRepository.execute(
              id,
              token,
              expirationTime()
        );

        return switch (result) {
            case AccountTokenPersisted accountTokenPersisted -> sendEmailAndPublishEvent(id, email, token);
            case AccountTokenPersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    private Succeeded sendEmailAndPublishEvent(
          UUID accountId,
          String email,
          String token) {

        sendEmail(accountId, email, token);
        applicationEventPublisher.publishEvent(new AccountCreated(this, accountId));
        return new Succeeded(accountId, authToken(accountId, email));

    }

    private String encryptPassword(
          String password) {

        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);

    }

    private String authToken(UUID id, String email) {

        return jwtService.generateToken(
              id,
              email
        );

    }

    private OffsetDateTime expirationTime() {

        return OffsetDateTime
              .now()
              .plusDays(1);

    }

    private void sendEmail(
          UUID accountId,
          String email,
          String token) {

        emailService.sendMessage(
              email,
              SUBJECT,
              ACTIVATE_ACCOUNT_TEMPLATE.formatted(baseUrl + token)
        );

    }
}
