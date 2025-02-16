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
import tech.javafullstack.accounts.domain.AddContactMessageService.Result.Failed;
import tech.javafullstack.accounts.domain.AddContactMessageService.Result.Succeeded;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageRequest;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersisted;
import tech.javafullstack.accounts.domain.PersistContactMessageRepository.PersistContactMessageResult.ContactMessagePersistenceFailed;

/**
 * Add contact message service implementation.
 */
@Service
@RequiredArgsConstructor
class AddContactMessageServiceImpl implements AddContactMessageService {
    private final PersistContactMessageRepository persistContactMessageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Result execute(
          Request contacMessage) {

        var result = persistContactMessageRepository.execute(
              new PersistContactMessageRequest(
                    contacMessage.subject(),
                    contacMessage.email(),
                    contacMessage.message())
        );

        return switch (result) {
            case ContactMessagePersisted(_) -> new Succeeded();
            case ContactMessagePersistenceFailed(Throwable cause) -> new Failed(cause);
        };

    }

}
