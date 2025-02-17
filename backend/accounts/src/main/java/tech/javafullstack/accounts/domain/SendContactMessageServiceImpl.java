// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.javafullstack.accounts.domain.SendContactMessageService.Result.Failed;
import tech.javafullstack.accounts.domain.SendContactMessageService.Result.MessagePolicyFailed;
import tech.javafullstack.accounts.domain.SendContactMessageService.Result.Succeeded;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageRequest;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersisted;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersistenceFailed;

/**
 * Add contact message service implementation.
 */
@Service
@RequiredArgsConstructor
class SendContactMessageServiceImpl implements SendContactMessageService {
    private static final String SUBJECT_IS_BLANK = "Subject is blank";
    private static final String EMAIL_IS_BLANK = "Email is blank";
    private static final String MESSAGE_IS_BLANK = "Message is blank";
    private static final String EMAIL_INVALID_FORMAT = "Email is not in right format";

    private final EmailValidationService emailValidationService;
    private final PersistContactMessageRepository persistContactMessageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          Request request) {

        var validationResult = validateInput(request);

        if (validationResult != null)
            return validationResult;

        var result = persistContactMessageRepository.execute(
              new PersistContactMessageRequest(
                    request.accountId(),
                    request.subject(),
                    request.email(),
                    request.message())
        );

        return switch (result) {
            case ContactMessagePersisted(_) -> new Succeeded();
            case ContactMessagePersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

    // -----------------------------------------------------------------------------------------------------------------
    // Private Section
    // -----------------------------------------------------------------------------------------------------------------

    private Result validateInput(
          Request contactMessage) {

        if (contactMessage.email() == null || contactMessage.email().isBlank()) {
            return new MessagePolicyFailed(EMAIL_IS_BLANK);
        }

        if (!emailValidationService.isEmailValid(contactMessage.email()))
            return new MessagePolicyFailed(EMAIL_INVALID_FORMAT);

        if (contactMessage.subject() == null || contactMessage.subject().isBlank()) {
            return new MessagePolicyFailed(SUBJECT_IS_BLANK);
        }

        if (contactMessage.message() == null || contactMessage.message().isBlank()) {
            return new MessagePolicyFailed(MESSAGE_IS_BLANK);
        }

        return null;

    }

}
